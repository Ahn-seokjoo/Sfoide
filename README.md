# RandomUser

## Introduction
---
[randomuser.me](https://randomuser.me/) 의 Api를 이용해서 User 정보를 가져옵니다.
page당 40명의 데이터를 가져와 RecyclerView에 표시하고, 무한 스크롤로 구현되어있으며 swipe 시에 데이터가 refresh되어 새로운 데이터가 set 됩니다.
MVVM 패턴과 Clean Architecture를 적용했으며, 다른 브랜치에 MVP 패턴또한 구현했습니다.
item을 클릭시에 상세 정보 페이지에 들어가며, 이메일과 핸드폰 번호를 클릭시 Intent를 통해 메일, 전화 기능을 추가했습니다. 

### Tech spec
---
- Kotlin

- MVVM Clean Architecture
- RxJava
- AAC ViewModel, LiveData, Databinding
- Hilt


### Project Structure
```
Sfoide
├── SfoideApplication.kt
├── data
│   └── repository
│       └── remote
│           ├── RemoteUserListDataSource.kt
│           ├── RemoteUserListDataSourceImpl.kt
│           ├── UserApi.kt
│           └── UserDataDto.kt
├── domain
│   ├── entities
│   │   ├── UserData.kt
│   │   ├── UserDataMapper.kt
│   │   └── enums
│   │       ├── Country.kt
│   │       └── Gender.kt
│   └── usecases
│       └── GetUserListUseCase.kt
├── ext
│   ├── BindingAdapter.kt
│   ├── EndlessRecyclerViewScrollListener.kt
│   └── SFoideModule.kt
└── presentation
    ├── userdetail
    │   └── UserDetailFragment.kt
    └── userlist
        ├── MainActivity.kt
        ├── UserListFragment.kt
        ├── UserListViewModel.kt
        └── recyclerview
            ├── UserListRecyclerViewAdapter.kt
            └── UserViewHolder.kt       
 ```           
 * **data**
   * repository
     * remote
     local이 없는, remote 층만 있기 때문에 repository로 한 층더 나누지 않고 바로 remotedatasource, remotedatasourceImpl을 구현했습니다. 서버와 통신에 필요한 Api와 요청 시에 return 값으로 받을 Dto를 가지고 있습니다.
 
 * **domain**
   * entities
   entitiy 계층은 안드로이드와 의존성이 전혀 없는, 순수 Java/Kotlin 코드로 구성되어야 합니다.
   통신으로 받아온 데이터를 객체에 사용할 수 있는, 필요한 데이터만 가지고 있는, 화면에서 정말 필요한 데이터만 가지고 있을수 있게 UserData로 변환해주는 Mapper와 entity인 UserData가 있습니다.
     * enum
       * Country 
     enum class로 국기 이모티콘과 국가 이름을 가지고 있으며, companion object로 getCountry 함수를 이용해 국가 이름을 입력해주어 국기 이모티콘을 얻습니다. 
       * Gender
     enum class로 성별과 성별 이모티콘을 가지고 있으며, companion object로 getGender 함수를 이용해 성별을 입력해주어 성별 이모티콘을 얻습니다.
     * **UserData**
     View에서 정말로 필요한 데이터만 모은, 순수 Kotlin 코드로 이루어진 data class 입니다.
     * UserDataMapper
     UserData를 UserDataDto로, UserDataDto를 UserData로 변환해줍니다.
    * usecases
      * **GetUserListUseCase**
       repository를 inject받아 data layer와 통신을 수행하고, 결과를 받아와 Mapper로 데이터를 변환해줍니다.
       Clean Architecture에서 하나의 피처(기능)마다 UseCase를 만들어주어 기능의 분리, 명확한 주제를 보장해줍니다.
 * **presentation**
   * userdetail
     * UserDetailFragment
     유저 아이템 클릭시에 보여지는 상세페이지에 대한 Fragment 입니다. 클릭된 유저의 정보와 Google Map을 이용한 위치를 지도에 표시합니다.
   * userlist
     * MainActivity
     Fragment들을 가지고 있는 Activity입니다. 뒤로가기 두번 클릭시 종료를 구현했습니다.
     * UserListFragment
     앱 첫 실행시 보이는 화면입니다. 유저들을 RecyclerView로 보여줍니다.
     * UserListViewModel
     use-case를 주입받아 데이터 요청을 수행하고, progress bar의 상태를 관리합니다.
     * recyclerView
     RecyclerView를 구현해 주었으며, 내부에서 data binding을 이용해 bind 해줍니다.
 * **ext**
   * BindingAdapter
     - Databinding에서 Glide를 이용한 Image bind, 이모티콘 연결, data submitList를 수행합니다.
   * EndlessRecyclerViewScrollListener
     - RecyclerView의 무한 스크롤을 수행할 수 있게 해줍니다.
   * SFoideModule
     - 객체를 주입해주고, 인터페이스의 구현체를 bind해줍니다.
   
 
 
### Result
 ![ezgif com-gif-maker (7)](https://user-images.githubusercontent.com/67602108/155758849-02a84e5d-f4e5-48d4-a4cd-f2d034ce7b32.gif)
