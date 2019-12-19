class WelcomeController < ApplicationController
  def index
    @latest_quote = HTTP.get("http://localhost:8086/quote").body
  end
end
