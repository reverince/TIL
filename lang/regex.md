# Regular Expression (Regex; 정규 표현식)

[RegExr](http://regexr.com) : 정규표현식 테스트 사이트

## 파이썬

```python
import re
regex = '(?P<year>\d+)\/(?P<month>\d+)\/(?P<day>\d+)'
s = '2018/12/13'
search = re.search(regex, s)
if search is not None:
  print(search.group('year'))  # => 2018
```

## 활용

* `/(?<target>[0-9A-Za-z가-힣]+)[을를] +/ =~ ipt` : 문자열 `ipt`에서 목적격 조사(`을`, `를`)가 붙은 단어를 찾아 변수 `target`에 저장한다.

## 조사 문제

`/(?<key>[0-9A-Za-z가-힣]+)로 +/ =~ ipt` : 도구격 조사(`으로`, `로`)의 구분이 필요하다.

* 지금은 `key = key[0..-2] if key[-1] == "으"`라는 별도 처리가 필요하다. 게다가 단어가 '으'로 끝나면 잘려버린다.
* `/(?<key>[0-9A-Za-z가-힣]+)(으로|로) +/`처럼 작성하면 `으` 자가 처음 캡처링 그룹에 포함되어서 실패.

### 해결

Lazy quantifier

* [커뮤니티 OKKY에서 얻은 답변](https://okky.kr/article/502191)
* 앞의 quantifier(수량사; `+`, `*` 등)가 최대한 적은 글자만을 매치하도록 하는 `?`를 사용한다.
* `/([0-9A-Za-z가-힣]+?)(으로|로)/`
