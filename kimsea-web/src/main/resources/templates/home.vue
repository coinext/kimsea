<!DOCTYPE html>
<html lang="en-US" dir="ltr">

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>KIM-SEA</title>
  <link rel="apple-touch-icon" sizes="180x180" href="../assets/img/favicons/apple-touch-icon.png">
  <link rel="icon" type="image/png" sizes="32x32" href="../assets/img/favicons/favicon-32x32.png">
  <link rel="icon" type="image/png" sizes="16x16" href="../assets/img/favicons/favicon-16x16.png">
  <link rel="shortcut icon" type="image/x-icon" href="../assets/img/favicons/favicon.ico">
  <link rel="manifest" href="../assets/img/favicons/manifest.json">
  <meta name="msapplication-TileImage" content="../assets/img/favicons/mstile-150x150.png">
  <meta name="theme-color" content="#ffffff">
  <script src="../assets/js/config.navbar-vertical.js"></script>
  <link rel="preconnect" href="https://fonts.gstatic.com/" crossorigin="">
  <link href="../assets/lib/perfect-scrollbar/perfect-scrollbar.css" rel="stylesheet">
  <link href="../assets/lib/owl.carousel/owl.carousel.css" rel="stylesheet">
  <link href="../assets/css/theme.css" rel="stylesheet">
  <link href="../assets/css/sweetalert2.css" rel="stylesheet">
  <link href="https://vjs.zencdn.net/7.17.0/video-js.css" rel="stylesheet" />
  <script src="../assets/lib/vue.min.js"></script>
  <script src="../assets/lib/vue-router.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/@vue/composition-api@1.0.5"></script>
  <script src="https://cdn.jsdelivr.net/npm/echarts@5.1.2"></script>
  <script src="https://cdn.jsdelivr.net/npm/vue-echarts@6.0.0"></script>
  <script th:inline="javascript">
    /*<![CDATA[*/
    const _user = /*[[${user}]]*/ false;
    const _networkChains = /*[[${networkChains}]]*/ false;
    const _categories = /*[[${categories}]]*/ false;
    const _status = /*[[${status}]]*/ false;
    /*]]>*/
  </script>
</head>


<body style="overflow-y: visible;">

<!-- ===============================================-->
<!--    Main Content-->
<!-- ===============================================-->
<div id="app">
  <main class="main" id="top">
    <div>
      <home-side-menu-view-component :_user="_user"></home-side-menu-view-component>
      <div class="content">
        <home-nav-view-component :_user="_user"></home-nav-view-component>
        <div class="row position-fixed no-gutters bg-300 rounded-soft px-card py-2 mb-3 z-index-10 w-100">
          <div class="col d-flex align-items-center">
            <div th:if="${user} != null" class="btn-available-balance mb-0 rounded rounded-capsule p-2">
              <div class="d-flex mr-2">
                  <span class="d-sm-none d-md-block"><i class="fas fa-gem mr-2 ml-2"></i>사용중 : <span class="ml-2 mr-2" v-text="wallet.usingGem"></span></span>
                  <span><i class="fas fa-gem mr-2 ml-2"></i>사용가능 : <span class="ml-2 mr-2" v-text="wallet.availableGem"></span></span>
              </div>
            </div>
            <div v-if="user != null" class="vertical-line vertical-line-400 position-relative h-100 mx-3 d-md-flex ml-3"></div>
            <div th:if="${user} != null" class="dropdown">
              <button class="btn btn-danger rounded-pill pl-3 pr-3" v-on:click="onCreateNftClick"><span class="fas fa-plus-circle mr-1"></span> NFT 만들기</button>
            </div>
          </div>
        </div>
        <div class="kanban-container ml-3">
          <router-view :key="$route.fullPath"></router-view>
        </div>
      </div>
    </div>
  </main>
</div>
<!-- ===============================================-->
<!--    End of Main Content-->
<!-- ===============================================-->




<!-- ===============================================-->
<!--    JavaScripts-->
<!-- ===============================================-->
<script src="../assets/js/jquery.min.js"></script>
<script src="../assets/js/popper.min.js"></script>
<script src="../assets/js/bootstrap.min.js"></script>
<script src="../assets/lib/@fortawesome/all.min.js"></script>
<script src="../assets/js/sweetalert2.js"></script>
<script src="../assets/lib/stickyfilljs/stickyfill.min.js"></script>
<script src="../assets/lib/sticky-kit/sticky-kit.min.js"></script>
<script src="../assets/lib/is_js/is.min.js"></script>
<script src="../assets/lib/lodash/lodash.min.js"></script>
<script src="../assets/lib/perfect-scrollbar/perfect-scrollbar.js"></script>
<script src="../assets/js/lazysizes.min.js"></script>
<link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,500,600,700%7cPoppins:100,200,300,400,500,600,700,800,900&amp;display=swap" rel="stylesheet">
<script src="../assets/lib/prismjs/prism.js"></script>
<script src="../assets/lib/chart.js/Chart.min.js"></script>
<script src="../assets/lib/owl.carousel/owl.carousel.js"></script>
<script src="../assets/lib/typed.js/typed.js"></script>
<script src="../assets/lib/video.min.js"></script>
<script src="../assets/lib/mitt.umd.js"></script>
<script src="../assets/lib/jquery-countdown/jquery.countdown.min.js"></script>
<script src="../assets/lib/scroll-loader.umd.min.js"></script>
<script type="module" src="../assets/lib/model-viewer.min.js"></script>
<script src="../assets/lib/vue-content-loader.umd.min.js"></script>

<script src="../assets/dist/js/utils/Utility.min.js"></script>
<script src="../assets/dist/js/providers/SimpleBaseProvider.min.js"></script>
<script src="../assets/dist/js/providers/AssetsProvider.min.js"></script>
<script src="../assets/dist/js/providers/NoticesProvider.min.js"></script>
<script src="../assets/dist/js/providers/WalletsProvider.min.js"></script>
<script src="../assets/dist/js/providers/OrdersProvider.min.js"></script>
<script src="../assets/dist/js/providers/AssetHistoriesProvider.min.js"></script>
<script src="../assets/dist/js/providers/UsersProvider.min.js"></script>
<script src="../assets/dist/js/providers/TransactionsProvider.min.js"></script>
<script src="../assets/dist/js/providers/StatsProvider.min.js"></script>
<script src="../assets/dist/js/providers/EventsProvider.min.js"></script>
<script src="../assets/dist/js/providers/MyApiProvider.min.js"></script>

<th:block th:include="'./components/eventViewComponent'"></th:block>
<th:block th:include="'./components/countdownViewComponent'"></th:block>
<th:block th:include="'./components/homeNavViewComponent'"></th:block>
<th:block th:include="'./components/homeSideMenuViewComponent'"></th:block>
<th:block th:include="'./components/popupCreateNftViewComponent'"></th:block>
<th:block th:include="'./components/assetViewComponent'"></th:block>
<th:block th:include="'./components/noticeAlertViewComponent'"></th:block>
<th:block th:include="'./components/popupBuyNftViewComponent'"></th:block>
<th:block th:include="'./components/popupSellNftViewComponent'"></th:block>
<th:block th:include="'./components/popupCancelSellNftViewComponent'"></th:block>
<th:block th:include="'./components/popupCancelBuyNftViewComponent'"></th:block>
<th:block th:include="'./components/popupWithdrawNftViewComponent'"></th:block>

<th:block th:include="'./panels/eventsPanel'"></th:block>
<th:block th:include="'./panels/noticesPanel'"></th:block>
<th:block th:include="'./panels/assetsPanel'"></th:block>
<th:block th:include="'./panels/assetPanel'"></th:block>
<th:block th:include="'./panels/walletsPanel'"></th:block>
<th:block th:include="'./panels/gemtoethPanel'"></th:block>
<th:block th:include="'./panels/statsPanel'"></th:block>
<th:block th:include="'./panels/apiPanel'"></th:block>

<script type="text/javascript">
  jQuery(document).ready(function($) {

    /*path,name : 모두 소문자*/
    const routes = [
      {path: '/mywallets', name:'myWallets', component: walletsPanel},
      {path: '/gemtoeth' , name:'gemtoeth', component: gemtoethPanel},
      {path: '/creator/:userId/assets', name:'userAssets', component: assetsPanel},
      {path: '/owner/:userId/assets', name:'ownerAssets', component: assetsPanel},
      {path: '/assets/:category', name:'assets', component: assetsPanel},
      {path: '/myassets/:status', name:'myassets', component: assetsPanel},
      {path: '/asset/:id', name:'asset', component: assetPanel},
      {path: '/events', name:'events', component: eventsPanel},
      {path: '/notices', name:'notices', component: noticesPanel},
      {path: '/notice/:id', name:'notice', component: noticesPanel},
      {path: '/stats/:type', name:'stats', component: statsPanel},
      {path: '/myapi', name:'myapi', component: apiPanel},
    ];

    const router = new VueRouter({
      routes,
      linkActiveClass: "active",
      linkExactActiveClass: "exact-active",
    });

    new Vue({
      router,
      el: '#app',
      components : {
        'home-nav-view-component' : homeNavViewComponent,
        'home-side-menu-view-component' : homeSideMenuViewComponent,
        'asset-view-component' : assetViewComponent,
        'content-loader' : contentLoaders.ContentLoader
      },
      data: {
        panels : routes,
        user:_user,
        wallet : {},
        myAssets:[],
      },
      watch: {
        $route(to, from) {
          if (to.path != from.path) {
            emitter.emit('onWalletLoad', null);
          }
        }
      }
      , created : function() {
        this.onWalletLoad();
      }
      , mounted : function () {
        emitter.on('onWalletLoad',this.onWalletLoad);
      }
      , methods : {
        onWalletLoad : function() {
          if (this.user == null) return;
          walletsProvider.getMyWallet(this, function ($this, result) {
            $this.wallet = result.data;
          });
        },
        onCreateNftClick : function() {
          utility.componentPopup("<strong>NFT 만들기</strong>", popupCreateNftViewComponent, {
            user : this.user,
                isLoading : false,
                networkChains : _networkChains,
                categories: _categories,
                attachFileMime : "image/*",
                networkChain : _networkChains[0],
                category : "PHOTO",
                name : "",
                description : "",
                attrs : [{"trait_type":"", "value":""}, {"trait_type":"", "value":""}, {"trait_type":"", "value":""}, {"trait_type":"", "value":""}, {"trait_type":"", "value":""}, {"trait_type":"", "value":""}]
          });
        }
      }
    });
  });
</script>
<script src="../assets/js/theme.js"></script>

</body>

</html>