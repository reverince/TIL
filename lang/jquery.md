# jQuery

```html
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
```

## 페이지 로딩 완료 대기

```js
$(function() {
  ...
});
```

## `change` 이벤트

```js
$('#class').change(function() {
  $('#race').empty().append('<option selected disabled>종족을 선택하세요!</option>').append('<option value="human">인간</option>')
  switch($(this).val()) {
    case 'thief':
      $('#race')
        .append('<option value="halfling">하플링</option>');
      break;
    ...
  }
});
```

- 드랍다운 업데이트에 활용.
