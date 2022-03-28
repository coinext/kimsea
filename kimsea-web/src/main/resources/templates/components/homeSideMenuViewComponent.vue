<script type="text/javascript">
const homeSideMenuViewComponent = {
  template: `<nav class="navbar navbar-vertical navbar-expand-xl navbar-dark bg-dark">
  <div class="d-flex align-items-center">
    <a class="navbar-brand ml-4" href="/">
      <div class="d-flex align-items-center py-3">
        <span class="text-sans-serif text-info">:: KIM-SEA</span>
      </div>
    </a>
  </div>
  <div class="collapse navbar-collapse" id="navbarVerticalCollapse">
    <div class="navbar-vertical-content perfect-scrollbar scrollbar p-3">
      <ul class="navbar-nav flex-column">
        <li class="nav-item">
            <router-link class="nav-link" exact-active-class="router-link-exact-active" :to="{name: 'assets' , params: {category: 'ALL'}}">
              <div class="d-flex align-items-center mb-1">
                <a><span class="nav-fs-2 fas fa-chart-pie mr-1"></span><span class="nav-fs-2">전체 NFT</span></a>
              </div>
            </router-link>
            <div class="d-flex align-items-center ml-3 mt-2" v-for="category in categories" v-if="category.name !='ALL'">
              <router-link class="nav-link" exact-active-class="router-link-exact-active" :to="{name: 'assets' , params: {category: category.name}}" replace>
                <a>
                  <i class="nav-fs-1 fas fa-bars"></i>
                  <span class="nav-fs-1" v-text="category.desc"></span>
                </a>
              </router-link>
            </div>
        </li>
        <div class="navbar-vertical-divider">
          <hr class="navbar-vertical-hr my-2" />
        </div>
        <li class="nav-item">
            <router-link class="nav-link" exact-active-class="router-link-exact-active" :to="{name: 'myassets', params: {status:'ALL'}}">
              <div class="d-flex align-items-center">
                <a><span class="nav-fs-2 fas fa-chart-pie mr-1"></span><span class="nav-fs-2">내 NFT</span></a>
              </div>
            </router-link>
            <div class="d-flex align-items-center ml-3 mt-2">
              <router-link class="nav-link" exact-active-class="router-link-exact-active" :to="{name: 'myassets', params: {status:'SELL'}}">
                <a>
                  <i class="nav-fs-1 fas fa-bars"></i>
                  <span class="nav-fs-1">판매중인 NFT</span>
                </a>
              </router-link>
            </div>
            <div class="d-flex align-items-center ml-3 mt-2">
              <router-link class="nav-link" exact-active-class="router-link-exact-active" :to="{name: 'myassets', params:{status:'BUY'}}">
                <a>
                  <i class="nav-fs-1 fas fa-bars"></i>
                  <span class="nav-fs-1">구매중인 NFT</span>
                </a>
              </router-link>
            </div>
            <div class="d-flex align-items-center ml-3 mt-2">
              <router-link class="nav-link" exact-active-class="router-link-exact-active" :to="{name: 'myassets', params:{status:'WITHDRAWING'}}">
                <a>
                  <i class="nav-fs-1 fas fa-bars"></i>
                  <span class="nav-fs-1">출금중인 NFT</span>
                </a>
              </router-link>
            </div>
            <div class="d-flex align-items-center ml-3 mt-2">
              <router-link class="nav-link" exact-active-class="router-link-exact-active" :to="{name: 'myassets', params:{status:'WITHDRAW'}}">
                <a>
                  <i class="nav-fs-1 fas fa-bars"></i>
                  <span class="nav-fs-1">출금된 NFT</span>
                </a>
              </router-link>
            </div>
        </li>
        <div class="navbar-vertical-divider">
          <hr class="navbar-vertical-hr my-2" />
        </div>
        <li class="nav-item">
          <router-link class="nav-link" exact-active-class="router-link-exact-active" to="/mywallets">
            <div class="d-flex align-items-center">
              <a><span class="nav-fs-2 fas fa-chart-pie mr-1"></span><span class="nav-fs-2">내지갑</span></a>
            </div>
          </router-link>
          <div class="d-flex align-items-center ml-3 mt-2">
            <router-link class="nav-link" exact-active-class="router-link-exact-active" to="/mywallets">
              <a>
                <i class="nav-fs-1 fas fa-bars"></i>
                <span class="nav-fs-1">GEM 환전하기</span>
              </a>
            </router-link>
          </div>
          <!--<div class="d-flex align-items-center ml-3 mt-2">
            <router-link class="nav-link" exact-active-class="router-link-exact-active" to="/gemtoeth">
              <a>
                <i class="nav-fs-1 fas fa-bars"></i>
                <span class="nav-fs-1">ETH 환전하기</span>
              </a>
            </router-link>
          </div>-->
        </li>
        <div class="navbar-vertical-divider">
          <hr class="navbar-vertical-hr my-2" />
        </div>
        <li class="nav-item">
          <router-link class="nav-link" exact-active-class="router-link-exact-active" to="/stats/users">
            <div class="d-flex align-items-center">
              <a><span class="nav-fs-2 fas fa-chart-pie mr-1"></span><span class="nav-fs-2">지표</span></a>
            </div>
          </router-link>
          <div class="d-flex align-items-center ml-3 mt-2">
            <router-link class="nav-link" exact-active-class="router-link-exact-active" to="/stats/creators">
              <a>
                <i class="nav-fs-1 fas fa-bars"></i>
                <span class="nav-fs-1">작가 활동</span>
              </a>
            </router-link>
          </div>
          <div class="d-flex align-items-center ml-3 mt-2">
            <router-link class="nav-link" exact-active-class="router-link-exact-active" to="/stats/assets">
              <a>
                <i class="nav-fs-1 fas fa-bars"></i>
                <span class="nav-fs-1">작품 순위</span>
              </a>
            </router-link>
          </div>
        </li>
        <!--<div class="navbar-vertical-divider">
          <hr class="navbar-vertical-hr my-2" />
        </div>
        <li class="nav-item">
          <router-link class="nav-link" :to="{name: 'events', params:{}}">
            <div class="d-flex align-items-center">
              <h5 class="text-700"><span class="fas fa-chart-pie mr-1"></span>이벤트</h5>
            </div>
          </router-link>
        </li>-->
        <div class="navbar-vertical-divider">
          <hr class="navbar-vertical-hr my-2" />
        </div>
        <li class="nav-item">
          <router-link class="nav-link" exact-active-class="router-link-exact-active" :to="{name: 'myapi', params:{}}">
            <div class="d-flex align-items-center">
              <a><span class="nav-fs-2 fas fa-chart-pie mr-1"></span><span class="nav-fs-2">API설정</span></a>
            </div>
          </router-link>
        </li>
        <div class="navbar-vertical-divider">
          <hr class="navbar-vertical-hr my-2" />
        </div>
        <li class="nav-item">
          <router-link class="nav-link" exact-active-class="router-link-exact-active" :to="{name: 'notices', params:{}}">
            <div class="d-flex align-items-center">
              <a><span class="nav-fs-2 fas fa-chart-pie mr-1"></span><span class="nav-fs-2">공지사항</span></a>
            </div>
          </router-link>
        </li>
      </ul>
    </div>
  </div>
  </nav>`
  , props : ['_user']
  , data : function() {
    return {
      user : this._user,
      categories : _categories
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