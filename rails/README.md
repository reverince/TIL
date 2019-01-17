# Ruby on Rails

상위 : [루비](/lang/ruby)

참조 : [Ruby on Rails Tutorial](https://www.railstutorial.org/book)

## 준비

1. 루비 2.5.1
1. `gem install rails -v 5.2.1`
1. `rails new revground`
1. `bundle install`
1. `rails server`

### `Gemfile`

```ruby
source 'https://rubygems.org'
git_source(:github) { |repo| "https://github.com/#{repo}.git" }

ruby '2.5.1'

# Bundle edge Rails instead: gem 'rails', github: 'rails/rails'
gem 'rails', '~> 5.2.1'
...
```

- `gem 'sqlite3'` : 최신 버전의 `sqlite3` 설치
- `gem 'uglifier', '>= 1.3.0'` : `1.3.0` 이상 버전의 `uglifier` 설치
- `gem 'rails', '~> 5.2.1'` : `5.2.1` 이상 `5.3.0` 미만 버전의 `rails` 설치

`bundle install`을 실행하면 `Gemfile`에 지정된 젬들을 설치한다.

## Hello, Rails!

###### app/controllers/application_controller.rb

```ruby
class ApplicationController < ActionController::Base

  def hello
    render html: "Hello, Rails!"
  end

end
```

###### config/routes.rb

```ruby
Rails.application.routes.draw do
  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html

  root 'application#hello'

end
```

## 라우터 (`config/routes.rb`)

```ruby
Rails.application.routes.draw do
  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
  root 'pages#home'
  get '/home', to: 'pages#home', as: :index
  get '/about', to: 'pages#about'
  resources :users
end
```

- `rails routes`
- `_path`는 루트(`/`)에 대한 상대 경로, `_url`은 절대 경로
- `resources` : 모델에 대한 RESTful 라우트 생성

| Action | HTTP 요청 | URL | 라우트 이름 |
|:-:|:-:|-|-|
|index|GET|/users|users_path|
|show|GET|/users/1|user_path(user)|
|new|GET|/users/new|new_user_path|
|create|POST|/users|users_path|
|edit|GET|/users/1/edit|edit_user_path(user)|
|update|PATCH|/users/1|user_path(user)|
|destroy|DELETE|/users/1|user_path(user)|

## 컨트롤러 (`app/controllers/`)

```
rails g controller Pages home about
```

```
rails destroy controller Pages home about
```

### 헬퍼 (`app/helpers/`)

###### `helpers/application_helper.rb`

```ruby
module ApplicationHelper
  $base_title = "Revground"

  def full_title(page_title="")
    (page_title.blank?) ? $base_title : "#{page_title} | #{$base_title}"
  end

end
```

어플리케이션이나 컨트롤러에서 사용할 함수를 정의할 수 있다.

## 뷰 (`app/views/`)

###### `views/layouts/application.html.erb`

```ruby
<!DOCTYPE html>
<html>
  <head>
    <title><%= full_title(yield(:title)) %></title>
    <%= csrf_meta_tags %>
    <%= csp_meta_tag %>

    <%= stylesheet_link_tag    'application', media: 'all', 'data-turbolinks-track': 'reload' %>
    <%= javascript_include_tag 'application', 'data-turbolinks-track': 'reload' %>
  </head>

  <body>
    <%= render 'layouts/header' %>
    <div class="container">
      <%= yield %>
      <%= render 'layouts/footer' %>
    </div>
  </body>
</html>
```

###### `views/layouts/_header.html.erb`

```ruby
<header class="navbar navbar-fixed-top navbar-inverse">
  <div class="container">
    <%= link_to $base_title, root_path, id: "logo" %>
    <nav>
      <ul class="nav navbar-nav navbar-right">
        <li><%= link_to "인덱스", root_path %></li>
        <li><%= link_to "대하여", about_path %></li>
      </ul>
    </nav>
  </div>
</header>
```

###### `views/pages/about.html.erb`

```ruby
<% provide(:title, "대하여") %>
<h1><%= yield(:title) %></h1>
<p>
  ...
</p>
```

- 변수 : `provide`와 `yield` 활용
- 레이아웃 : `render`와 `_<NAME>.html.erb` 활용

## 테스트 (`test/`)

###### `test/controllers/`

```ruby
class PagesControllerTest < ActionDispatch::IntegrationTest

  def setup
    @base_title = "Revground"
  end

  test "should get home" do
    get home_path
    assert_response :success
    assert_select "title", "인덱스 | #@base_title"
  end

end
```

- `rails test` 혹은 `rails t`
  - `rails test:models`처럼 부분 테스트 가능
- `def setup` : 테스트 시작 전 자동으로 실행되는 메소드

### 픽스처 (`test/fixtures`)

```yml
# Read about fixtures at http://api.rubyonrails.org/classes/ActiveRecord/FixtureSet.html

one:
  name: fixture1
  email: fix1@test.com

two:
  name: fixture2
  email: fixture2@test.com
```

테스트 샘플 데이터

## 모델 (`app/models` / `db/migrate`)

```
rails g model User name:string email:string
```

###### `db/migrate/<TIMESTAMP>_create_users.rb`

```ruby
class CreateUsers < ActiveRecord::Migration[5.2]
  def change
    create_table :users do |t|
      t.string :name
      t.string :email

      t.timestamps
    end
  end
end
```

```
rails db:migrate
```

- `rails console --sandbox`로 테스트
  ```
  user = User.new(name: "cho", email: "reverince@gmail.com")
  user.valid?
  user.save
  user.created_at
  user.reload.name

  creation_yon = User.create(name: "yon", email: "yonzun@gmail.com")
  creation_yon.destroy

  User.find(2)
  User.find_by(name: "cho")
  User.all
  ```

###### `models/user.rb`

```ruby
class User < ApplicationRecord
  EMAIL_REGEX = /\A([\w+\-].?)+@[a-z\d\-]+(\.[a-z]+)*\.[a-z]+\z/i
  validates :name,  presence: true, uniqueness: true,
                    length: { maximum: 50 }
  validates :email, presence: true, uniqueness: true,
                    length: { maximum: 255 },
                    format: { with: EMAIL_REGEX }
  has_secure_password
  before_save { self.email.downcase! }
end
```

### 마이그레이션

```
rails g migration add_index_to_users_email
```

###### `db/migrate/<TIMESTAMP>_add_index_to_users_email.rb`

```ruby
class AddIndexToUsersEmail < ActiveRecord::Migration[5.2]
  def change
    add_index :users, :email, unique: true
  end
end
```

```
rails g migration add_password_digest_to_users password_digest:string
```

## 비밀번호 구현

1. `Gemfile`에 `gem 'bcrypt', '~> 3.1.12'`

1. `models/user.rb` 파일에 `has_secure_password`

1. DB에 `password_digest` 속성
  ###### `db/migrate/<TIMESTAMP>_add_password_digest_to_users.rb`

  ```ruby
  class AddPasswordDigestToUsers < ActiveRecord::Migration[5.2]
    def change
      add_column :users, :password_digest, :string
    end
  end
  ```

  - 마이그레이션 이름이 `to_users`로 끝나기 때문에 레일즈에서 자동으로 `:users`를 입력해 준다.

1. `rails db:migrate`

1. 테스트 수정
  ###### `test/models/user_test.rb`
  ```ruby
  require 'test_helper'

  class UserTest < ActiveSupport::TestCase
    def setup
      @user = User.new(name: "YJ Cho", email: "yjcho@test.com", password: "1234", password_confirmation: "1234")
    end

    ...
  end
  ```


- `user.authenticate(password)`로 인증
