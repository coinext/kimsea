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
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Russo+One&display=swap" rel="stylesheet">
  <script src="../assets/lib/vue.min.js"></script>
  <script src="../assets/lib/vue-router.min.js"></script>
  <script th:inline="javascript">
    /*<![CDATA[*/
    const _user = /*[[${user}]]*/ false;
    const _categories = /*[[${categories}]]*/ false;
    /*]]>*/
  </script>
</head>


<body>
<div id="app">
<main class="main" id="top">
  <landing-nav-view-component :_user="_user" :_categories="_categories"></landing-nav-view-component>

  <section class="py-0 overflow-hidden" id="banner">
    <div class="bg-holder overlay bg-gradient-bora"></div>
    <div class="container">
      <div class="row justify-content-center align-items-center pt-8 pt-lg-10 pb-lg-9 pb-xl-0 pl-sm-3 pr-sm-3 p-3">
        <div class="col-md-11 col-lg-8 col-xl-4 pb-7 pb-xl-9 text-center text-xl-left"><a class="btn btn-outline-danger mb-4 fs--1 border-2x rounded-pill" href="#!">NFT 마켓플레이스</a>
          <h2 class="text-white font-weight-light">나만의 디지털 자산<br /><span class="typed-text font-weight-bold mr-2" data-typed-text='["간편하게","안전하게","멋있게"]'></span> 만들고,<br/>마켓에서 거래하세요.</h2>
          <p class="d-sm-none d-md-block lead text-white opacity-75 mt-3">블록체인을 이용한 나만의 유일한 디지털자산(NFT)을 만드는 모험을 떠나보세요. 안전성과 신뢰성이 기반이 된 블록체인기반으로 동작하고, 어떤것으로부터도 안전합니다.</p><a class="btn btn-outline-light border-2x rounded-pill btn-lg mt-4 fs-0 py-2" href="/home#/assets/ALL">시작하기<span class="fas fa-play" data-fa-transform="shrink-6 down-1 right-5"></span></a>
        </div>
        <div class="d-sm-none d-md-block col-xl-7 offset-xl-1 align-self-end"><a class="img-landing-banner" href="/home#/assets/ALL"><img class="img-fluid" src="../assets/img/generic/bg-item3.png" alt="" /></a></div>
      </div>
    </div>
  </section>

  <section class="py-2 bg-light shadow-sm p-0">
    <div class="container">
      <div class="row justify-content-center align-items-center">
          <div class="col-6 col-md-4 col-xl mb-4 text-center">
            <span class="font-counter fs-4 font-weight-medium" th:data-count="${statistics.totalAssetsCnt}" data-countup='{"format":"comma","duration":3000}'>0</span>
            <h5 class="border-top border-300 p-2"><i class="far fa-image mr-1"></i>총 등록된 NFT</h5>
          </div>
        <div class="d-sm-none d-md-block col-6 col-md-4 col-xl mb-4 text-center">
            <span class="font-counter fs-4 font-weight-medium" th:data-count="${statistics.totalMovieAssetsCnt}" data-countup='{"format":"comma","duration":3000}'>0</span>
            <h5 class="border-top border-300 p-2"><i class="fas fa-video mr-1"></i>등록된 동영상 NFT</h5>
          </div>
          <div class="d-sm-none d-md-block col-6 col-md-4 col-xl mb-4 text-center">
            <span class="font-counter fs-4 font-weight-medium" th:data-count="${statistics.totalMusicAssetsCnt}" data-countup='{"format":"comma","duration":3000}'>0</span>
            <h5 class="border-top border-300 p-2"><i class="fas fa-music mr-1"></i>등록된 음악 NFT</h5>
          </div>
          <div class="d-sm-none d-md-block col-6 col-md-4 col-xl mb-4 text-center">
            <span class="font-counter fs-4 font-weight-medium" th:data-count="${statistics.totalSellingAssetsCnt}" data-countup='{"format":"comma","duration":3000}'>0</span>
            <h5 class="border-top border-300 p-2"><i class="fas fa-shuttle-van mr-1"></i>판매중인 NFT</h5>
          </div>
          <div class="col-6 col-md-4 col-xl mb-4 text-center">
            <span class="font-counter fs-4 font-weight-medium" th:data-count="${statistics.totalTradedAmount}" data-countup='{"format":"comma","duration":3000}'>0</span>
            <h5 class="border-top border-300 p-2"><i class="far fa-gem mr-1"></i>판매총액수</h5>
          </div>
      </div>
    </div>
  </section>

  <section class="pl-3 text-center">
    <div class="container">
      <div class="row">
        <div class="col">
          <h1 class="fs-2 fs-sm-4 fs-md-5 font-weight-bold"><span class="fab fa-buffer mr-1"></span>최근 신규등록 NFTs</h1>
          <p class="lead">NFT는 사진, 비디오, 오디오 및 기타 유형의 디지털 파일의 소유를 나타내는데 사용할 수 있습니다.</p>
        </div>
      </div>
      <div class="row mt-md-6 mt-sm-2">
        <!--<content-loader :speed="2": animate="true"></content-loader>-->
        <div class="col-lg-3 mt-6 mb-6 mt-lg-0" v-for="asset in assets">
          <asset-view-component :_user="_user" :_asset="asset"></asset-view-component>
        </div>
      </div>
    </div>
  </section>

  <section class="pl-3 text-center bg-light">
    <div class="container">
      <div class="row">
        <div class="col">
          <h1 class="fs-2 fs-sm-4 fs-md-5 font-weight-bold"><span class="fab fa-buffer mr-1"></span>최고 인기 NFTs TOP20</h1>
          <p class="lead">NFT는 사진, 비디오, 오디오 및 기타 유형의 디지털 파일의 소유를 나타내는데 사용할 수 있습니다.</p>
        </div>
      </div>
      <div class="row mt-md-6 mt-sm-2">
        <div class="col-lg-3 mt-6 mb-6 mt-lg-0" v-for="asset in popularAssets">
          <asset-view-component :_user="_user" :_asset="asset"></asset-view-component>
        </div>
      </div>
    </div>
  </section>

  <section class="d-sm-none d-md-block pl-3">
    <div class="container">
      <div class="row justify-content-center text-center">
        <div class="col-lg-8 col-xl-7 col-xxl-6">
          <h1 class="fs-2 fs-sm-4 fs-md-5">KIMSEA는 어떤곳인가?</h1>
          <p class="lead">`NFT`를 누구나 수수료없이 무료로 만들고, 자유롭게 판매하는 NFT 오픈마켓입니다.</p>
        </div>
      </div>
      <div class="row align-items-center justify-content-center mt-8">
        <div class="col-md col-lg-5 col-xl-4 pl-lg-6"><img class="img-fluid px-6 px-md-0" src="../assets/img/features/feature1.png" alt="" /></div>
        <div class="col-md col-lg-5 col-xl-4 mt-4 mt-md-0">
          <h5 class="text-danger"><span class="far fa-lightbulb mr-2"></span>GEM</h5>
          <h3>GEM포인트 사용</h3>
          <p>이더리움과 일정비율을 유지하는 GEM포인트를 내부적으로 사용하고, 해당 GEM과 ETH간의 환전이 가능하여 보다 유연한 거래가 가능합니다.</p>
        </div>
      </div>
      <div class="row align-items-center justify-content-center mt-7">
        <div class="col-md col-lg-5 col-xl-4 pr-lg-6 order-md-2"><img class="img-fluid px-6 px-md-0" src="../assets/img/features/feature2.png" alt="" /></div>
        <div class="col-md col-lg-5 col-xl-4 mt-4 mt-md-0">
          <h5 class="text-info"> <span class="far fa-object-ungroup mr-2"></span>거래</h5>
          <h3>입찰방식의 거래</h3>
          <p>NFT의 거래는 입찰가를 등록하고, 입찰완료일이 되면, 가장높은 입찰가로 낙찰이 되는 방식의 거래를 지원합니다. 입찰을 통해, 좀더 가치있는 NFT의 거래를 보장할수 있습니다.</p>
        </div>
      </div>
      <div class="row align-items-center justify-content-center mt-7">
        <div class="col-md col-lg-5 col-xl-4 pl-lg-6"><img class="img-fluid px-6 px-md-0" src="../assets/img/features/feature3.png" alt="" /></div>
        <div class="col-md col-lg-5 col-xl-4 mt-4 mt-md-0">
          <h5 class="text-success"><span class="far fa-paper-plane mr-2"></span>스탠다드 NFT 메타 지원</h5>
          <h3>오픈씨 NFT 메타형식 호환</h3>
          <p>발행되는 NFT는 최대 NFT마켓인 OPENSEA.IO의 NFT 형식과 호환이 됩니다.발행하여, 오픈씨에서도 NFT거래가 가능합니다.</p>
        </div>
      </div>
    </div>
  </section>

  <section class="py-0 bg-dark">
    <div>
      <hr class="my-0 border-600 opacity-25" />
      <div class="container py-3">
        <div class="row justify-content-between fs--1">
          <div class="col-12 col-sm-auto text-center">
            <p class="mb-0 text-600 opacity-85">Copyright © KIMSEA. All rights reserved. <span class="d-none d-sm-inline-block">| </span><br class="d-sm-none" /> <a class="text-white opacity-85" href="/info/privacy">개인정보처리방침</a></p>
          </div>
          <div class="col-12 col-sm-auto text-center">
            <p class="mb-0 text-600 opacity-85">v2.8.0</p>
          </div>
        </div>
      </div>
    </div>
  </section>
</main>
</div>

<!-- ===============================================-->
<!--    JavaScripts-->
<!-- ===============================================-->
<script src="../assets/js/jquery.min.js"></script>
<!--<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/jquery.lazy/1.7.9/jquery.lazy.min.js"></script>-->
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
<script src="../assets/lib/owl.carousel/owl.carousel.js"></script>
<script src="../assets/lib/typed.js/typed.js"></script>
<script src="../assets/lib/video.min.js"></script>
<script src="../assets/lib/mitt.umd.js"></script>
<script src="../assets/lib/vue-content-loader.umd.min.js"></script>
<script type="module" src="../assets/lib/model-viewer.min.js"></script>

<script src="../assets/dist/js/utils/Utility.min.js"></script>
<script src="../assets/dist/js/providers/SimpleBaseProvider.min.js"></script>
<script src="../assets/dist/js/providers/AssetsProvider.min.js"></script>
<script src="../assets/dist/js/providers/WalletsProvider.min.js"></script>
<script src="../assets/dist/js/providers/OrdersProvider.min.js"></script>
<script src="../assets/dist/js/providers/AssetHistoriesProvider.min.js"></script>
<script src="../assets/dist/js/providers/UsersProvider.min.js"></script>

<th:block th:include="'./components/countdownViewComponent'"></th:block>
<th:block th:include="'./components/landingNavViewComponent'"></th:block>
<th:block th:include="'./components/assetViewComponent'"></th:block>
<th:block th:include="'./components/popupBuyNftViewComponent'"></th:block>
<th:block th:include="'./components/popupSellNftViewComponent'"></th:block>
<th:block th:include="'./components/popupCancelBuyNftViewComponent'"></th:block>
<th:block th:include="'./components/popupCancelSellNftViewComponent'"></th:block>
<th:block th:include="'./components/popupWithdrawNftViewComponent'"></th:block>

<script type="text/javascript">

  jQuery(document).ready(function($) {

    new Vue({
      el: '#app',
      components : {
        'landing-nav-view-component' : landingNavViewComponent,
        'asset-view-component' : assetViewComponent,
        'content-loader' : contentLoaders.ContentLoader
      },
      data: {
        assets:[],
        popularAssets:[]
      }
      , mounted : function() {

      }
      , created : function() {
        const query = '';
        assetsProvider.getRecentlyAssets(this, function($this, result) {
          $this.assets = result.data;
        }, query);

        assetsProvider.getPopularAssets(this, function($this, result) {
          $this.popularAssets = result.data;
        }, query);
      }
      , methods : {
          onLogout() {
           alert("logout");
         }
      }
    });
  });
</script>
<script src="../assets/js/theme.js"></script>
</body>

</html>