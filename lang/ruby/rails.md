# Ruby on Rails

상위 : [루비](../ruby)

* 라우터 : `config/routes.rb`

* 스타일시트 : `app/assets/stylesheets`

* 메일러
`rails g mailer UserMailer account_activation password_reset`

* 모델(DB)
  * `rails g model post (rails g migration ...)
db/migrate/...create_posts.rb`
  * `rails db:migrate`
  * `rails db:migrate:reset  # 초기화`
  * `rails db:seed  # seeds.rb 적용`

* 스캐폴딩
`rails g scaffold post title:string content:text`

### 오류 해결

* `ActionController::InvalidAuthenticityToken`
  * 앱 컨트롤러 `protect_from_forgery with: :exception` 주석 처리
