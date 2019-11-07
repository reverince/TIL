# Donkeycar

## 구성

### 1. 라즈베리 파이에 Raspbian Lite OS 설치 ([가이드](https://www.raspberrypi.org/documentation/installation/installing-images/))

### 2. 와이파이 설정

`boot` 파티션에 `wpa_supplicant.conf` 파일을 만들어 다음과 같은 내용으로 저장

```
country=US
ctrl_interface=DIR=/var/run/wpa_supplicant GROUP=netdev
update_config=1

network={
    ssid="네트워크 이름"
    psk="비밀번호"
}
```

### 3. SSH 활성화

이름이 `ssh`인 파일을 `boot` 파티션 최상위에 배치

SD 카드를 라즈베리 파이에 장착

### 4. 연결

#### Ubuntu

`ping raspberrypi.local`

`ssh pi@rawspberrypi.local`

#### Windows

1. 파이에 모니터와 키보드를 연결하고 `pi` / `raspberry`로 로그인
1. `ifconfig wlan0`
1. `ssh pi@<파이 IP 주소>` 또는 PuTTY에서 `pi` / `raspberry`로 연결

### 4. 업데이트

```
sudo apt-get update
sudo apt-get upgrade
```

### 5. Raspi-config

`sudo raspi-config`
- 비밀번호 변경
- 호스트 이름 변경
- 인터페이스 옵션 활성화 등

### 6. 의존 패키지 설치

`sudo apt-get install build-essential python3 python3-dev python3-pip python3-virtualenv python3-numpy python3-picamera python3-pandas python3-rpi.gpio i2c-tools avahi-utils joystick libopenjp2-7-dev libtiff5-dev gfortran libatlas-base-dev libopenblas-dev libhdf5-serial-dev git ntp`

(선택) OpenCV

`sudo apt-get install libilmbase-dev libopenexr-dev libgstreamer1.0-dev libjasper-dev libwebp-dev libatlas-base-dev libavcodec-dev libavformat-dev libswscale-dev libqtgui4 libqt4-test
`

### 7. 가상 환경 구성

```
python3 -m virtualenv -p python3 env --system-site-packages
echo "source env/bin/activate" >> ~/.bashrc
source ~/.bashrc
```

###  8. Donkeycar 파이썬 코드 설치

```
git clone https://github.com/autorope/donkeycar
cd donkeycar
git checkout master
pip install -e .[pi]
pip install tensorflow==1.13.1
```

(선택) OpenCV

`sudo apt install python3-opencv` 또는 `pip install opencv-python`

TensorFlow 및 OpenCV 설치 확인

```
python -c "import tensorflow"
python -c "import cv2"
```

### 9. Donkeycar 앱 생성

```
donkey createcar --path ~/mycar
```

### 10. 설정

`myconfig.py`

## 캘리브레이팅

### 스티어링 캘리브레이션

1. 차량 전원을 켠다.
1. 차량에서 servo 케이블을 찾아 PCA 보드의 어느 채널에 연결되어 있는지 확인한다. (`1` 또는 `0`)
1. `donkey calibrate --channel <채널> --bus=1`
1. `360`을 입력하면 바퀴가 움직인다.
1. 값을 10씩 조절하면서 바퀴가 왼쪽과 오른쪽으로 최대한 돌아가는 PWM 세팅 값을 찾는다.
1. 찾은 값을 `myconfig.py`의 `STEERING_RIGHT_PWM`과 `STEERING_LEFT_PWM`에 입력한다.

### 스로틀 캘리브레이션

1. ESC에서 나오는 케이블을 찾아 PCA 보드의 어느 채널에 연결되어 있는지 확인한다.
1. `donkey calibrate --channel <채널> --bus=1`
1. PWD 값을 입력하라는 프롬프트가 나오면 `370`을 입력한다.
1. ESC에서 값이 조정됐음을 알리는 비프음이 울린다.
1. `400`을 입력하면 바퀴가 앞쪽으로 움직여야 한다. 뒤로 움직인다면 대신 `330`을 입력한다.
1. 값을 조절하며 최대 속도에서의 PWM 값을 기억해둔다.

최대 후진 속도

1. 위와 같은 방법으로 정지 상태에서의 PWM 값을 설정한다.
1. 역방향의 값을 입력하고, 정지 값을 입력하고, 역방향의 값을 다시 입력한다.
1. 값을 10씩 조절하며 최대 후진 속도에서의 PWM 값을 기억해둔다.  

`myconfig.py`에서 다음 항목을 입력한다.
- `THROTTLE_FOWARD_PWM` : 최대 전진 속도에서의 PWM 값
- `THROTTLE_STOPPED_PWM` : 정지 상태의 PWM 값
- `THROTTLE_REVERSE_PWM` : 최대 후진 속도에서의 PWM 값

## 운전

`python manage.py drive`로 운전을 시작하고
브라우저에서 `<파이 IP 주소>:8887`에 접속한다.

### 웹 컨트롤러 조작

#### 기능

- 녹화 : 영상, 스티어링 각도, 스로틀 값을 기록할 수 있다.
- 스로틀 모드 : 스로틀을 고정할 수 있다.
- 파일럿 모드 : 파일럿이 스티어링 또는 스로틀을 직접 제어할 때 선택한다.

#### 단축키

- `space` : 차량을 정지하고 녹화를 중단한다.
- `r` : 녹화를 켜거나 끈다.
- `i` : 가속
- `k` : 감속
- `j` : 좌회전
- `l` : 우회전

## 자율주행 훈련

### 데이터 수집

1. 트랙을 따라 직접 주행을 연습한다.
1. 실수 없이 10바퀴를 주행할 수 있는 수준이 되면 파이썬 `manage.py` 프로세스를 다시 시작해 새로운 세션을 만든다.
1. 녹화를 시작하고 트랙을 따라 주행한다. (조이스틱은 속도가 0이 아니면 자동으로 녹화)
1. 주행 중 트랙에서 벗어나거나 사고가 나면 즉시 `Stop Car` 버튼을 눌러 녹화를 중단한다. 조이스틱은 △ 버튼을 누르면 최근 5초의 기록이 지워진다.
1. 좋은 데이터를 10~20바퀴 수집하면 `Ctrl+C`로 SSH 세션을 종료한다.
1. 최신 tub 폴더의 `data` 폴더에서 수집한 데이터를 확인할 수 있다.

### 파이에서 컴퓨터로 데이터 전송

```
rsync -r pi@<파이 IP 주소>:~/mycar/data/ ~/mycar/data/
```

### 모델 훈련

```
python ~/mycar/manage.py train --tub <쉼표로 구분된 tub 폴더명> --model ./models/mypilot.h5
```

tub 폴더는 `./data/*`, `/data/tub_?_19_12_13` 등으로 선택할 수 있다. `--tub` 옵션이 없으면 기본 `data` 폴더의 모든 tub이 사용된다.

훈련이 끝나면 다시 파일럿을 차량으로 옮긴다.

```
rsync -r ~/mycar/models/ pi@<파이 IP 주소>:~/mycar/models/
```

훈련한 모델으로 차량을 주행시킨다.

```
python manage.py drive --model ~/mycar/models/mypilot.h5
```
