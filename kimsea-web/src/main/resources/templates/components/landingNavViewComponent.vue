<script type="text/javascript">
const landingNavViewComponent = {
  template: `<nav class="navbar navbar-standard navbar-expand-lg fixed-top navbar-dark navbar-theme">
  <div class="container">
    <a class="navbar-brand border-bottom border-400" href="/"><span class="text-white">:: KIM-SEA</span></a>
    <button class="navbar-toggler collapsed" type="button" data-toggle="collapse" data-target="#navbarStandard" aria-controls="navbarStandard" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
    <div class="collapse navbar-collapse scrollbar perfect-scrollbar" id="navbarStandard">
      <ul class="navbar-nav">
        <li class="nav-item dropdown dropdown-on-hover">
          <a class="nav-link dropdown-toggle" id="navbarDropdownHome" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">NFT마켓</a>
          <div class="dropdown-menu dropdown-menu-card" aria-labelledby="navbarDropdownHome">
            <div class="bg-white rounded-soft py-2">
              <a class="dropdown-item" v-for="category in categories"  :href="'/home#/assets/' + category.name" v-text="category.desc"></a>
            </div>
          </div>
        </li>
        <li class="nav-item dropdown dropdown-on-hover">
          <a class="nav-link dropdown-toggle" id="navbarDropdownPages" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">지표</a>
          <div class="dropdown-menu dropdown-menu-card" aria-labelledby="navbarDropdownPages">
            <div class="bg-white rounded-soft py-2">
              <a class="dropdown-item" href="/home#/stats/users">유저</a>
              <a class="dropdown-item" href="/home#/stats/creators">작가</a>
              <a class="dropdown-item" href="/home#/stats/assets">작품</a>
            </div>
          </div>
        </li>
        <li class="nav-item dropdown dropdown-on-hover">
          <a class="nav-link dropdown-toggle" id="navbarDropdownDocumentation" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">API</a>
          <div class="dropdown-menu dropdown-menu-card" aria-labelledby="navbarDropdownDocumentation">
              <div class="bg-white rounded-soft py-2">
                <a class="dropdown-item" href="/info/api">API문서</a>
              </div>
          </div>
        </li>
        <li class="nav-item dropdown dropdown-on-hover">
          <a class="nav-link dropdown-toggle" id="navbarDropdownComponents" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">공지</a>
          <div class="dropdown-menu dropdown-menu-card" aria-labelledby="navbarDropdownComponents">
            <div class="bg-white rounded-soft py-2">
              <a class="dropdown-item" href="/info/terms">약관</a>
              <a class="dropdown-item" href="/info/privacy">개인정보처리방침</a>
              <a class="dropdown-item" href="/info/usage">사용법</a>
              <a class="dropdown-item" href="/home#/notices">공지사항</a>
          </div>
        </li>
      </ul>
      <ul class="navbar-nav ml-auto">
        <li class="nav-item">
          <a v-if="user != null" class="btn btn-outline-danger fs--1 border-2x rounded-pill mb-1" href="/logout"><img class="avatar avatar-m rounded-circle border border-500 border-danger shadow-sm mr-3 p-0 m-0" :src="user.profileImgUrl"/><span v-text="user.profile"></span></a>
          <a v-else class="btn btn-outline-light fs--1 border-2x rounded-pill mb-1" href="/login"><i class="fas fa-user-alt mr-1"></i>로그인하기</a>
        </li>
      </ul>
    </div>
  </div>
  </nav>`
  , props : ['_user', '_categories']
  , data : function() {
    return {
      user : this._user,
      categories : this._categories
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