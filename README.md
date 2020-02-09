# FlickrApp
This is an Flickr gallery app  using Architecture Components (ViewModel, LiveData & RxJava)

## PRESENTATION
This simple app consists of  two screens
* PhotoList(Fetch (Retrofit) the flickr public photos)
* PhotoDetail(Show the detailed info when clicked on a photo from PhotoList)

### Architecture Components
This application implements the following concepts :
- ViewModel
- LiveData
- RxJava 2
- Dagger 2
- MockWebServer

### Libraries
* [Android Architecture Components][arch]
* [Dagger 2][dagger2] for dependency injection
* [RxJava 2][rxjava2] for composing asynchronous and event-based programs using observable sequences
* [Retrofit][retrofit] for REST api communication
* [Glide][glide] for image loading
* [MockWebServer][mockwebserver] for testing HTTP clients

## PREVIEW
![Initial Loading](/screenshots/loading.png?raw=true "Optional Title")
![Photo List](/screenshots/photo_list.png?raw=true "Optional Title")
![Sort by date](/screenshots/order_by.png?raw=true "Optional Title")
![Search via tag](/screenshots/search_tag.png?raw=true "Optional Title")
![Enter tag by text](/screenshots/search_edittext.png?raw=true "Optional Title")
![Search results](/screenshots/search_results.png?raw=true "Optional Title")
![Photo Detail](/screenshots/photo_detail.png?raw=true "Optional Title")


