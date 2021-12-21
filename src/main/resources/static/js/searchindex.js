// search index for WYSIWYG Web Builder
var database_length = 0;

function SearchPage(url, title, keywords, description)
{
   this.url = url;
   this.title = title;
   this.keywords = keywords;
   this.description = description;
   return this;
}

function SearchDatabase()
{
   database_length = 0;
   this[database_length++] = new SearchPage("wb17_shopper.html", "Shopper", "NEW INSPIRATION 2021  25% OFF ON NEW SEASON  NEW INSPIRATION 2021  25% OFF ON NEW SEASON  NEW INSPIRATION 2021  25% OFF ON NEW SEASON  SUPPORT  COMPANY  Copyright © 2021 WYSIWYG Web Builder    Lorem ipsum dolor sit amet, consectetur adipiscing elit Integer nec odio Praesent libero Sed cursus ante dapibus diam Sed nisi Nulla quis sem at nibh elementum imperdiet   STAY UP TO DATE  #28, 3nd floor, WYSIWYG PlazaWeb City, Builder, WB 1969 Phone  100 121 34567 Fax  100 121 34568 Email  info@wysiwygmail.com  SHOPPER  LOREM IPSUM DOLOR SIT AMET  Lorem ipsum dolor sit amet, consectetur adipiscing elit Integer nec odio Praesent libero Sed cursus ante dapibus diam Sed nisi Nulla quis sem at nibh elementum imperdiet Duis sagittis ipsum Praesent mauris Fusce nec tellus sed augue semper porta    ", "");
   return this;
}
