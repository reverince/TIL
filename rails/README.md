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
