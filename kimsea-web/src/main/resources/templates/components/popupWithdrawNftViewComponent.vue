<script type="text/javascript">
const popupWithdrawNftViewComponent = {
  template: `<div id="withdrawNftPopup">
  <div class="fix-m-4" role="document">
    <div class="modal-content">
      <div class="modal-body">
        <div class="row d-flex no-gutters">
          <div class="d-sm-none d-md-block col-5 pl-lg-2 mb-3">
            <div class="card h-100 text-left">
              <div class="bg-light card-body">
                <h5>NFT</h5>
                <img class="lazyload card-img-top h-auto" :src="asset.prevImgUrl" v-bind:data-src="asset.imgUrl">
                <hr>
                <h5>설명</h5>
                <p class="card-text multiline-ellipsis" v-text="asset.description"></p>
                <hr>
              </div>
            </div>
          </div>
          <div class="col pl-lg-2 mb-3">
            <div class="card h-100 text-left">
              <div class="bg-light card-body">
                <h5><span class="fas fa-wallet mr-1"></span>내 지갑현황</h5>
                <div class="card">
                  <div class="card-body form-group">
                    <div class="btn-sm btn-using-balance mb-0 rounded rounded-capsule pl-2 pr-2 mb-1">
                      <i class="fas fa-gem mr-2 ml-2"></i>사용중 GEM : <span class="ml-3 mr-3" v-text="wallet.usingGem"></span>
                    </div>
                    <div class="btn-sm btn-available-balance mb-0 rounded rounded-capsule pl-2 pr-2">
                      <i class="fas fa-gem mr-2 ml-2"></i>사용가능 GEM : <span class="ml-3 mr-3" v-text="wallet.availableGem"></span>
                    </div>
                  </div>
                </div>
                <br/>
                <hr>
                <h5><span class="far fa-paper-plane mr-1"></span>전송할 지갑주소</h5>
                <div class="form-group">
                  <input class="form-control" v-model="toAddress" placeholder="전송할 지갑주소를 입력하세요" type="text"/>
                </div>
                <hr>
                <br/>
                <h5 class="mt-1 text-danger"><span class="fab fa-adobe mr-1"></span>주의사항</h5>
                <span class="card-text fs--1 mb-1 text-danger font-weight-bold">출금주소를 꼭 확인하고, 잘못된 주소로 출금시의 책임은 본인에게 있음을 동의합니다.</span>
                <div class="form-group form-check mt-2">
                  <input class="form-check-input" id="agreeCheckBox" type="checkbox" v-model="agreement">
                  <label class="form-check-label" for="agreeCheckBox">동의합니다.</label>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button class="btn btn-secondary btn-sm" type="button" v-on:click="onClosePopupClick()">닫기</button>
        <button class="btn btn-primary btn-sm" type="button" v-on:click="onWithdrawNftClick()">출금요청하기</button>
      </div>
    </div>
  </div>
  <div :class="{'wrap-loading':true, 'display-none':!isLoading}">
    <div class="spinner-border text-info text-center" role="status">
      <span class="sr-only">Loading...</span>
    </div>
  </div>
  </div>`
  , el : "#withdrawNftPopup"
  , data : function() {
    return {
      user : {},
      asset : {},
      wallet : {},
      toAddress : null,
      agreement : true,
      isLoading : false,
    }
  }
  , mounted : function() {
  }
  , created: function() {
    this.onWalletLoad();
  }
  , methods : {
    onWalletLoad : function() {
      walletsProvider.getMyWallet(this, function ($this, result) {
        $this.wallet = result.data;
      });
    },
    onClosePopupClick : function() {
      swal.close();
    }
    , onWithdrawNftClick : function() {
      if (this.agreement == false) {
        utility.errorAlert("에러", "주의사항을 읽고, 출금 동의 해주세요.");
        return;
      }

      if (this.toAddress == null || this.toAddress.length <= 40) {
        utility.errorAlert("에러", "출금주소를 정확히 입력해주세요.");
        return;
      }

      this.isLoading = true;
      assetsProvider.withdraw(this, function ($this, result) {
        $this.isLoading = false;
        if (result.code == "C0000") {
          utility.successAlert("NFT 출금","출금요청 성공하였습니다!", function() {
            window.location.reload();
          });
        } else {
          utility.errorCodeAlert("NFT 출금 실패",result.code, result.msg, function() {
            window.location.reload();
          });
        }
      }, this.asset.id, this.toAddress);
    }
  }
};
</script>