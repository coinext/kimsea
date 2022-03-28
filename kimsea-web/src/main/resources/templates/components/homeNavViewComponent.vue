<script type="text/javascript">
const homeNavViewComponent = {
  template: `<nav class="navbar navbar-dark navbar-top sticky-kit navbar-expand bg-dark">
  <button class="btn navbar-toggler-humburger-icon navbar-toggler mr-1 mr-sm-3" type="button" data-toggle="collapse" data-target="#navbarVerticalCollapse" aria-controls="navbarVerticalCollapse" aria-expanded="false" aria-label="Toggle Navigation"><span class="navbar-toggle-icon"><span class="toggle-line"></span></span></button>
  <a class="navbar-brand mr-1 mr-sm-3" href="/">
    <div class="d-flex align-items-center">
      <span class="text-sans-serif text-info">:: KIM-SEA</span>
    </div>
  </a>
  <ul class="navbar-nav align-items-center d-none d-lg-block">
    <li class="nav-item ml-3">
      <div class="search-box">
        <div class="position-relative">
          <input id="searchInputId" class="form-control search-input" type="search" placeholder="검색하려는 NFT의 이름을 적어주세요." aria-label="Search" />
          <span class="fas fa-search search-box-icon"></span>
        </div>
      </div>
    </li>
  </ul>
  <ul class="navbar-nav navbar-nav-icons ml-auto flex-row align-items-center">
    <li class="nav-item dropdown dropdown-on-hover">
      <a v-if="user != null" class="btn btn-outline-danger fs--1 border-2x rounded-pill mb-1" href="/logout"><img class="avatar avatar-m rounded-circle border border-500 border-danger shadow-sm mr-3 p-0 m-0" :src="user.profileImgUrl"/><span v-text="user.profile"></span></a>
      <a v-else class="btn btn-outline-light fs--1 border-2x rounded-pill mb-1" href="/login"><i class="fas fa-user-alt mr-1"></i>로그인하기</a>
    </li>
    <li class="nav-item">
      <a class="nav-link pr-0" href="#">
        <div class="avatar avatar-xl w-25"></div>
      </a>
    </li>
  </ul>
  </nav>`
  , props : ['_user']
  , data : function() {
    return {
      user : this._user
    }
  }
  , mounted : function() {

  }
  , created: function() {

  }
  , methods : {
  }
};
</script>