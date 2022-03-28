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
        <div class="row justify-content-center align-items-center p-1">
          <div class="col-md-11 col-lg-8 col-xl-4 pb-7 pb-xl-6 text-center text-xl-left">
          </div>
        </div>
      </div>
    </section>

    <section class="pl-3 text-center">
      <div class="container">
        <div class="row">
          <div class="col">
            <h1 class="fs-2 fs-sm-4 fs-md-5 font-weight-bold"><span class="fab fa-buffer mr-1"></span>KIM-SEA 이용법</h1>
            <p class="lead text-left mt-6">
              * 거래 이용<br/><br/>
              - 거래는 옥션(입찰)방식으로 진행됩니다.<br/>
              - 거래는 최초 입찰시간부터 5시간동안 진행되며, 마감이 되고, 입찰처리가 됩니다.<br/>
              - 입찰가가 가장높은 가격으로 입찰이 됩니다.<br/>
              - 입찰처리시에 입찰되지 않은 포인트는 환급되며, 입찰시에는 거래 수수료를 부과됩니다.<br/>
              - 입찰가는 마감전까지 수정,취소가 가능합니다.<br/><br/>

              * 출금 이용<br/><br/>
              - 실제 NFT발행은 출금시에 이루어지며, 이때 발행,출금 수수료가 부과됩니다.<br/>
              - 출금시 잘못된 주소나 다른 네트워크로 출금되지않게 주의하시기 바랍니다.<br/><br/>

              * 입금(환전) 이용<br/><br/>
              - 스마트컨트랙트 주소에서 보낸 이더리움은 입금처리가 되지않습니다. 꼭 보내실때, 보내는 주소를 확인해주세요.<br/>
              - 입금된 이더리움과의 일정비율로 GEM이 환전됩니다. (1ETH : 50000GEM 비율)<br/>
            </p>
          </div>
        </div>
        <div class="row mt-md-6 mt-sm-2">
          <div class="col-lg-3 mt-6 mb-6 mt-lg-0">

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

<th:block th:include="'./components/landingNavViewComponent'"></th:block>

<script type="text/javascript">
  jQuery(document).ready(function($) {

    new Vue({
      el: '#app',
      components : {
        'landing-nav-view-component' : landingNavViewComponent,
      },
      data: {
      }
      , mounted : function() {
      }
      , created : function() {

      }
      , methods : {
      }
    });
  });
</script>
<script src="../assets/js/theme.js"></script>
</body>

</html>