# To start server
# ruby server.rb

require 'sinatra'
require 'json'

set :bind, '0.0.0.0'

get "/posts.json" do
   {:value => rand(0..10)}.to_json
end
