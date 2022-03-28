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
          <h1 class="fs-2 fs-sm-4 fs-md-5 font-weight-bold"><span class="fab fa-buffer mr-1"></span>API문서</h1>
          <p class="lead">API를 이용하여 사용자의 어플리케이션에서 자유롭게 NFT를 발행,판매 관리할수있습니다.</p>
        </div>
      </div>
      <br/>
      <div class="row mt-3">
        <div class="col-lg-3 mt-6 mb-6 mt-lg-0 border pt-3 pb-3">
          <h5>내자산 생성하기<br/>
          (POST)
          </h5>
        </div>
        <div class="col-lg-9 mt-6 mb-6 mt-lg-0 border pt-3 pb-3">
          <h5>/api/v1/myassets/createAsset</h5><br/>
          <h6 class="bg-300 p-3">
            <div class="bg-info p-1">
              REQUEST
            </div>
            <br/>
            header : api-key : String : APIKEY<br/>
            body : prevImageFile : MultipartFile : 대표이미지 썸네일<br/>
            body : imageFile : MultipartFile : 대표이미지<br/>
            body : attachFile : MultipartFile : 첨부파일<br/>
            body : networkChain : String : ETHEREUM<br/>
            body : category : String : NFT 카테고리<br/>
            body : name : String : NFT 이름<br/>
            body : description : String : NFT 설명<br/>
            body : attrs : String : NFT 속성<br/>
          </h6>
          <h6 class="bg-300 p-3">
            <div class="bg-info p-1">
              RESPONSE
            </div>
            <br/>
            {<br/>
            data : id : Long : NFT ID<br/>
            data : creator : Object : NFT생성자 정보<br/>
            data : owner : Object : NFT소유자 정보<br/>
            data : category : NFT카테고리<br/>
            data : mime : String : NFT첨부파일 종류<br/>
            data : status : String : NFT상태<br/>
            data : nftAddress : String : NFT주소<br/>
            data : name : String : NFT이름<br/>
            data : price : Long : NFT 가격<br/>
            data : description : String : NFT설명<br/>
            data : imgUrl : String : 대표이미지 주소<br/>
            data : prevImgUrl : String : 대표이미지 KIMSEA 주소<br/>
            data : thumbImgUrl : String : 대표이미지 IPFS 주소<br/>
            data : attachFileUrl : String : 첨부파일 IPFS 주소<br/>
            data : prevAttachFileUrl : String : 첨부파일 KIMSEA 주소<br/>
            data : metadataUrl : String : 메타정보 IPFS 주소<br/>
            data : attrs : String : NFT속성<br/>
            data : regDttm : Date : NFT 생성일자<br/>
            data : bidEndDttm : Date : NFT BID종료일자<br/>
            data : delDttm : Date : NFT 삭제일자<br/>
            data : mintDttm : Date : NFT 발행일자<br/>
            }<br/>
          </h6>
        </div>
      </div>

      <div class="row mt-3">
        <div class="col-lg-3 mt-6 mb-6 mt-lg-0 border pt-3 pb-3">
          <h5>내자산 정보얻기<br/>
            (GET)
          </h5>
        </div>
        <div class="col-lg-9 mt-6 mb-6 mt-lg-0 border pt-3 pb-3">
          <h5>/api/v1/myassets/getMyAssetsByStatus</h5><br/>
          <h6 class="bg-300 p-3">
            <div class="bg-info p-1">
              REQUEST
            </div>
            <br/>
            header : api-key : String : APIKEY<br/>
            param : status : String : NFT상태<br/>
          </h6>
          <h6 class="bg-300 p-3">
            <div class="bg-info p-1">
              RESPONSE
            </div>
            <br/>
            [<br/>
            data : id : Long : NFT ID<br/>
            data : creator : Object : NFT생성자 정보<br/>
            data : owner : Object : NFT소유자 정보<br/>
            data : category : NFT카테고리<br/>
            data : mime : String : NFT첨부파일 종류<br/>
            data : status : String : NFT상태<br/>
            data : nftAddress : String : NFT주소<br/>
            data : name : String : NFT이름<br/>
            data : price : Long : NFT 가격<br/>
            data : description : String : NFT설명<br/>
            data : imgUrl : String : 대표이미지 주소<br/>
            data : prevImgUrl : String : 대표이미지 KIMSEA 주소<br/>
            data : thumbImgUrl : String : 대표이미지 IPFS 주소<br/>
            data : attachFileUrl : String : 첨부파일 IPFS 주소<br/>
            data : prevAttachFileUrl : String : 첨부파일 KIMSEA 주소<br/>
            data : metadataUrl : String : 메타정보 IPFS 주소<br/>
            data : attrs : String : NFT속성<br/>
            data : regDttm : Date : NFT 생성일자<br/>
            data : bidEndDttm : Date : NFT BID종료일자<br/>
            data : delDttm : Date : NFT 삭제일자<br/>
            data : mintDttm : Date : NFT 발행일자<br/>
            ]<br/>
          </h6>
        </div>
      </div>

      <div class="row mt-1">
        <div class="col-lg-3 mt-6 mb-6 mt-lg-0 border pt-3 pb-3">
          <h5>내자산 판매하기<br/>
            (POST)
          </h5>
        </div>
        <div class="col-lg-9 mt-6 mb-6 mt-lg-0 border pt-3 pb-3">
          <h5>/api/v1/myassets/sellAsset</h5><br/>
          <h6 class="bg-300 p-3">
            <div class="bg-info p-1">
              REQUEST
            </div>
            <br/>
            header : api-key : String : APIKEY<br/>
            body : assetId : Long : NFT ID<br/>
            body : price : Long : 판매가격<br/>
          </h6>
          <h6 class="bg-300 p-3">
            <div class="bg-info p-1">
              RESPONSE
            </div>
            <br/>
            {<br/>
            data : id : Long : 주문 ID<br/>
            data : assetId : Long : NFT ID<br/>
            data : userId : Long : USER ID<br/>
            data : from : Object : ORDER 요청 유저정보<br/>
            data : to : Object : ORDER 승인 유저정보<br/>
            data : price : Long : 가격<br/>
            data : fee : Long : 수수료<br/>
            data : status : String : 주문 상태<br/>
            data : description : String : 주문 설명<br/>
            data : regDttm : Date : 주문등록 일자<br/>
            data : completedDttm : Date : 주문 완료일자<br/>
            }<br/>
          </h6>
        </div>
    </div>

      <div class="row mt-1">
        <div class="col-lg-3 mt-6 mb-6 mt-lg-0 border pt-3 pb-3">
          <h5>내자산 판매취소<br/>
            (POST)
          </h5>
        </div>
        <div class="col-lg-9 mt-6 mb-6 mt-lg-0 border pt-3 pb-3">
          <h5>/api/v1/myassets/cancelSellAsset</h5><br/>
          <h6 class="bg-300 p-3">
            <div class="bg-info p-1">
              REQUEST
            </div>
            <br/>
            header : api-key : String : APIKEY<br/>
            body : assetId : Long : NFT ID<br/>
          </h6>
          <h6 class="bg-300 p-3">
            <div class="bg-info p-1">
              RESPONSE
            </div>
            <br/>
            {<br/>
            data : id : Long : 주문 ID<br/>
            data : assetId : Long : NFT ID<br/>
            data : userId : Long : USER ID<br/>
            data : from : Object : ORDER 요청 유저정보<br/>
            data : to : Object : ORDER 승인 유저정보<br/>
            data : price : Long : 가격<br/>
            data : fee : Long : 수수료<br/>
            data : status : String : 주문 상태<br/>
            data : description : String : 주문 설명<br/>
            data : regDttm : Date : 주문등록 일자<br/>
            data : completedDttm : Date : 주문 완료일자<br/>
            }<br/>
          </h6>
        </div>
      </div>

      <div class="row mt-1">
        <div class="col-lg-3 mt-6 mb-6 mt-lg-0 border pt-3 pb-3">
          <h5>내자산 판매수정<br/>
            (POST)
          </h5>
        </div>
        <div class="col-lg-9 mt-6 mb-6 mt-lg-0 border pt-3 pb-3">
          <h5>/api/v1/myassets/modifySellAsset</h5><br/>
          <h6 class="bg-300 p-3">
            <div class="bg-info p-1">
              REQUEST
            </div>
            <br/>
            header : api-key : String : APIKEY<br/>
            body : assetId : Long : NFT ID<br/>
            body : price : Long : 판매가격<br/>
          </h6>
          <h6 class="bg-300 p-3">
            <div class="bg-info p-1">
              RESPONSE
            </div>
            <br/>
            {<br/>
            data : id : Long : 주문 ID<br/>
            data : assetId : Long : NFT ID<br/>
            data : userId : Long : USER ID<br/>
            data : from : Object : ORDER 요청 유저정보<br/>
            data : to : Object : ORDER 승인 유저정보<br/>
            data : price : Long : 가격<br/>
            data : fee : Long : 수수료<br/>
            data : status : String : 주문 상태<br/>
            data : description : String : 주문 설명<br/>
            data : regDttm : Date : 주문등록 일자<br/>
            data : completedDttm : Date : 주문 완료일자<br/>
            }<br/>
          </h6>
        </div>
      </div>

      <div class="row mt-1">
        <div class="col-lg-3 mt-6 mb-6 mt-lg-0 border pt-3 pb-3">
          <h5>자산 구매<br/>
            (POST)
          </h5>
        </div>
        <div class="col-lg-9 mt-6 mb-6 mt-lg-0 border pt-3 pb-3">
          <h5>/api/v1/myassets/buyAsset</h5><br/>
          <h6 class="bg-300 p-3">
            <div class="bg-info p-1">
              REQUEST
            </div>
            <br/>
            header : api-key : String : APIKEY<br/>
            body : assetId : Long : NFT ID<br/>
            body : price : Long : 판매가격<br/>
          </h6>
          <h6 class="bg-300 p-3">
            <div class="bg-info p-1">
              RESPONSE
            </div>
            <br/>
            {<br/>
            data : id : Long : 주문 ID<br/>
            data : assetId : Long : NFT ID<br/>
            data : userId : Long : USER ID<br/>
            data : from : Object : ORDER 요청 유저정보<br/>
            data : to : Object : ORDER 승인 유저정보<br/>
            data : price : Long : 가격<br/>
            data : fee : Long : 수수료<br/>
            data : status : String : 주문 상태<br/>
            data : description : String : 주문 설명<br/>
            data : regDttm : Date : 주문등록 일자<br/>
            data : completedDttm : Date : 주문 완료일자<br/>
            }<br/>
          </h6>
        </div>
      </div>

      <div class="row mt-1">
        <div class="col-lg-3 mt-6 mb-6 mt-lg-0 border pt-3 pb-3">
          <h5>자산 구매취소<br/>
            (POST)
          </h5>
        </div>
        <div class="col-lg-9 mt-6 mb-6 mt-lg-0 border pt-3 pb-3">
          <h5>/api/v1/myassets/cancelBuyAsset</h5><br/>
          <h6 class="bg-300 p-3">
            <div class="bg-info p-1">
              REQUEST
            </div>
            <br/>
            header : api-key : String : APIKEY<br/>
            body : assetId : Long : NFT ID<br/>
          </h6>
          <h6 class="bg-300 p-3">
            <div class="bg-info p-1">
              RESPONSE
            </div>
            <br/>
            {<br/>
            data : id : Long : 주문 ID<br/>
            data : assetId : Long : NFT ID<br/>
            data : userId : Long : USER ID<br/>
            data : from : Object : ORDER 요청 유저정보<br/>
            data : to : Object : ORDER 승인 유저정보<br/>
            data : price : Long : 가격<br/>
            data : fee : Long : 수수료<br/>
            data : status : String : 주문 상태<br/>
            data : description : String : 주문 설명<br/>
            data : regDttm : Date : 주문등록 일자<br/>
            data : completedDttm : Date : 주문 완료일자<br/>
            }<br/>
          </h6>
        </div>
      </div>

      <div class="row mt-1">
        <div class="col-lg-3 mt-6 mb-6 mt-lg-0 border pt-3 pb-3">
          <h5>자산 구매수정<br/>
            (POST)
          </h5>
        </div>
        <div class="col-lg-9 mt-6 mb-6 mt-lg-0 border pt-3 pb-3">
          <h5>/api/v1/myassets/modifyBuyAsset</h5><br/>
          <h6 class="bg-300 p-3">
            <div class="bg-info p-1">
              REQUEST
            </div>
            <br/>
            header : api-key : String : APIKEY<br/>
            body : assetId : Long : NFT ID<br/>
            body : price : Long : 판매가격<br/>
          </h6>
          <h6 class="bg-300 p-3">
            <div class="bg-info p-1">
              RESPONSE
            </div>
            <br/>
            {<br/>
            data : id : Long : 주문 ID<br/>
            data : assetId : Long : NFT ID<br/>
            data : userId : Long : USER ID<br/>
            data : from : Object : ORDER 요청 유저정보<br/>
            data : to : Object : ORDER 승인 유저정보<br/>
            data : price : Long : 가격<br/>
            data : fee : Long : 수수료<br/>
            data : status : String : 주문 상태<br/>
            data : description : String : 주문 설명<br/>
            data : regDttm : Date : 주문등록 일자<br/>
            data : completedDttm : Date : 주문 완료일자<br/>
            }<br/>
          </h6>
        </div>
      </div>

      <div class="row mt-1">
        <div class="col-lg-3 mt-6 mb-6 mt-lg-0 border pt-3 pb-3">
          <h5>내자산 출금하기<br/>
            (POST)
          </h5>
        </div>
        <div class="col-lg-9 mt-6 mb-6 mt-lg-0 border pt-3 pb-3">
          <h5>/api/v1/myassets/withdraw</h5><br/>
          <h6 class="bg-300 p-3">
            <div class="bg-info p-1">
              REQUEST
            </div>
            <br/>
            header : api-key : String : APIKEY<br/>
            body : assetId : Long : NFT ID<br/>
            body : toAddress : String : 출금주소<br/>
          </h6>
          <h6 class="bg-300 p-3">
            <div class="bg-info p-1">
              RESPONSE
            </div>
            <br/>
            {<br/>
            body : id : String : 트랜잭션 ID<br/>
            body : blockNum : BigInteger : 블록넘버<br/>
            body : userId : Long : USER ID<br/>
            body : coin : String : COIN종류<br/>
            body : orgTx : String : BLOCKCHAIN TXID<br/>
            body : category : String : 트랜잭션종류<br/>
            body : status : String : 트랜잭션상태<br/>
            body : assetId : String : NFT ID<br/>
            body : fromAddress : String : from주소<br/>
            body : toAddress : String : to주소<br/>
            body : amount : BigDecimal : ETH 수량<br/>
            body : gemAmount : BigDecimal : GEM 수량<br/>
            body : fee : BigDecimal : 수수료<br/>
            body : confirm : Int : 컨펌수<br/>
            body : revertReason : String : 트랜잭션 실행 실패사유<br/>
            body : retryCnt : Int : 트랜잭션 재실행 횟수<br/>
            body : createdDttm : Date : 트랜잭션 생성일자<br/>
            body : regDttm : Date : 트랜잭션 등록일자<br/>
            body : completedDttm : Date : 트랜잭션 완료일자<br/>
            }<br/>
          </h6>
        </div>
      </div>

      <div class="row mt-1">
        <div class="col-lg-3 mt-6 mb-6 mt-lg-0 border pt-3 pb-3">
          <h5>내지갑 정보<br/>
            (GET)
          </h5>
        </div>
        <div class="col-lg-9 mt-6 mb-6 mt-lg-0 border pt-3 pb-3">
          <h5>/api/v1/wallets/getMyWallet</h5><br/>
          <h6 class="bg-300 p-3">
            <div class="bg-info p-1">
              REQUEST
            </div>
            <br/>
            header : api-key : String : APIKEY<br/>
          </h6>
          <h6 class="bg-300 p-3">
            <div class="bg-info p-1">
              RESPONSE
            </div>
            <br/>
            {<br/>
            data : userId : Long : 유저 ID<br/>
            data : usingGem : Long : 사용중인 GEM<br/>
            data : availableGem : Long : 사용가능 GEM<br/>
            data : address : String : 지갑주소<br/>
            data : regDttm : Date : 지갑 생성일자<br/>
            }<br/>
          </h6>
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
      },
      data: {
        assets:[],
        popularAssets:[]
      }
      , mounted : function() {

      }
      , created : function() {

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