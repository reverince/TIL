# Markdown

### 제목 / 가로줄
```
# H1
## H2
...
###### H6
```

---
가로줄 : (3개 이상의) `---` / `***` / `___`

### 리스트
1. 순서 리스트 : `1. 순서`
2. 두 번째 : `1. 두 번째` / `2. 두 번째` / `- 두 번째` / `* 두 번째`


- 비순서 리스트 : `- 비순서` / `* 비순서`

※ 리스트를 구분하려면 2줄 이상 비워야 한다.

### 글자 서식
- `코드` : `` `코드` ``
- *기울임* : `*기울임*` / `_기울임_`
- **굵게** : `**굵게**` / `__굵게__`
- ***혼합*** : `***혼합***` / `___혼합___`

### 링크 / 이미지
[깃헙](https://github.com) `[깃헙](https://github.com)`

![대체 텍스트](https://www.google.co.kr/images/branding/googlelogo/2x/googlelogo_color_120x44dp.png) `![대체 텍스트](https://www.google.co.kr/images/branding/googlelogo/2x/googlelogo_color_120x44dp.png)`

### 신택스 하이라이팅
```python
def synhi:
	str = "신택스 하이라이팅"
	print(str)
	return True
end
```
````
```python
def synhi:
	str = "신택스 하이라이팅"
	print(str)
	return True
end
```
````

### 표
| 왼쪽 | 가운데 | 오른쪽 |
|-|:-:|-:|
| 1번 | 알파 | 10 |
| 2번 | 오메가 | 5 |
```
| 왼쪽 | 가운데 | 오른쪽 |
|-|:-:|-:|
| 1번 | 알파 | 10 |
| 2번 | 오메가 | 5 |
```

### 기타

- HTML 태그를 마크다운에 넣을 때 : `markdown="1"`
  ```html
  <div markdown="1" ...
  ```
