<script type="text/javascript">
const walletsPanel = {
  template: `
    <div class="text-center bg-light" id="containerId">
    <div class="container mt-6">
      <div class="row">
        <div class="col">
          <notice-alert-view-component></notice-alert-view-component>
          <h1 class="fs-2 fs-sm-4 fs-md-5 mt-6">내 지갑 : ETH -> GEM 환전</h1>
          <p class="lead">대체 불가능한 토큰은 블록체인에 저장된 데이터 단위로, 고유하면서 상호 교환할 수 없는 토큰을 뜻합니다.</p>
        </div>
      </div>
      <div v-if="user == null" class="row mt-6">
        <div class="col-lg-12 mt-6 mb-6 mt-lg-0">
          <span class="mb-3">로그인을 하시면 볼수있습니다.</span><br/><br/>
          <a class="btn btn-outline-dark fs--1 border-2x rounded-pill mb-1" href="/login"><i
              class="fas fa-user-alt mr-1"></i>로그인하기</a>
        </div>
      </div>
      <div v-else>
        <div class="row mt-6">
          <div class="col-lg-12 mt-6 mb-3 mt-lg-0">
            <div class="card card-span h-100">
              <div class="card-body pt-4 pb-1">
                <ul class="list-group list-group-flush pt-0 mt-0 pb-3">
                  <h5 class="card-title text-left mb-2 mt-1"># 내지갑주소</h5>
                  <li class="list-group-item text-left">
                    <h3 class="card-text" v-text="wallet.address"></h3>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
        <div class="row mt-1">
          <div class="col-lg-7 mt-2 mb-6 mt-lg-0">
            <div class="card card-span h-100">
              <div class="card-body pt-4 pb-1">
                <ul class="list-group list-group-flush pt-0 mt-0 pb-3">
                  <h5 class="card-title text-left mb-2 mt-1"># GEM 환전내역 (1ETH = 50000GEM)</h5>
                  <div class="scrollbar perfect-scrollbar" style="height: 800px; overflow-y: scroll;" id="depositTxDivId">
                  <li class="list-group-item text-left" v-for="depositTx in depositTxs">
                    <span class="card-text">
                      <span class="font-weight-bold" v-text="'ETH ' + depositTx.amount + '개 ==> GEM ' + depositTx.gemAmount + '개'"></span>
                      <br/>
                      <div class="mt-2">
                        <span v-if="depositTx.status == 'COMPLETED'">
                          <span class="text-info font-weight-bold">[환전완료]</span>
                          <span v-html="readableDateString(depositTx.completedDttm)"></span>
                        </span>
                        <span v-else-if="depositTx.status == 'PENDING'">
                          <span class="text-success font-weight-bold">[환전중]</span>
                          <span v-html="readableDateString(depositTx.regDttm)"></span>
                        </span>
                        <span v-else-if="depositTx.status == 'CANCEL'">
                          <span class="text-danger font-weight-bold">[환전취소]</span>
                          <span v-html="readableDateString(depositTx.regDttm)"></span>
                        </span>
                        <a :href="'http://etherscan.io/tx/' + depositTx.orgTx" target="_blank">
                          <span class="ml-3" v-text="depositTx.orgTx"></span>
                        </a>
                      </div>
                    </span>
                  </li>
                    <scroll-loader :loader-method="onDepositTransactionLoad" :loader-disable="isDisableDepositTxLoading">
                      <div></div>
                    </scroll-loader>
                  </div>
                </ul>
              </div>
            </div>
          </div>
          <div class="col-lg-5 mt-2 mb-6 mt-lg-0">
            <div class="card card-span h-100">
              <div class="card-body pt-4 pb-1">
                <ul class="list-group list-group-flush pt-0 mt-0 pb-3">
                  <h5 class="card-title text-left mb-2 mt-1"># NFT 출금내역</h5>
                  <div class="scrollbar perfect-scrollbar" style="height: 800px; overflow-y: scroll;" id="withdrawTxDivId">
                  <li class="list-group-item text-left" v-for="withdrawTx in withdrawTxs">
                    <span class="card-text">
                      <img class="avatar avatar-3xl rounded-circle border border-500 shadow-sm mr-3" :src="withdrawTx.asset.prevImgUrl" alt=""/>
                      <span class="mr-2" v-text="'[' + withdrawTx.asset.category + ']'"></span>
                      <span class="font-weight-bold" v-text="withdrawTx.asset.name"></span>
                      <br/>
                      <div class="mt-2">
                        <span v-if="withdrawTx.status == 'COMPLETED'">
                          <span class="text-info font-weight-bold">[출금완료]</span>
                          <span v-html="readableDateString(withdrawTx.completedDttm)"></span>
                        </span>
                        <span v-else-if="withdrawTx.status == 'PENDING'">
                          <span class="text-success font-weight-bold">[출금신청]</span>
                          <span v-html="readableDateString(withdrawTx.regDttm)"></span>
                        </span>
                        <span v-else-if="withdrawTx.status == 'PROCESS'">
                          <span class="text-secondary font-weight-bold">[출금중]</span>
                          <span v-html="readableDateString(withdrawTx.regDttm)"></span>
                        </span>
                        <span v-else-if="withdrawTx.status == 'CANCEL'">
                          <span class="text-danger font-weight-bold">[출금취소]</span>
                          <span v-html="readableDateString(withdrawTx.regDttm)"></span>
                        </span>
                        <a :href="'http://etherscan.io/address/' + withdrawTx.toAddress" target="_blank">
                          <span class="ml-3" v-text="withdrawTx.toAddress"></span>
                        </a>
                      </div>
                    </span>
                  </li>
                  <scroll-loader :loader-method="onWithdrawTransactionLoad" :loader-disable="isDisableWithdrawTxLoading">
                    <div></div>
                  </scroll-loader>
                  </div>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    </div>`
  , components : {
    'notice-alert-view-component' : noticeAlertViewComponent,
  }
  , data : function() {
    return {
      user : _user,
      notice : {},
      wallet : {},
      params : this.$route.params,
      query : this.$route.query,
      depositTxs : [],
      withdrawTxs : [],
      pageNoWithdrawTxs : 0,
      pageSizeWithdrawTxs : 10,
      isDisableWithdrawTxLoading: false,

      pageNoDepositTxs : 0,
      pageSizeDepositTxs : 10,
      isDisableDepositTxLoading: false,
    }
  }
  , created: function() {
    this.type = "wallet";
    this.onWalletLoad();
  }
  , methods : {
    onWithdrawTransactionLoad : function() {
      if (this.user == null) return;
      transactionsProvider.getMyWithdrawTransactions(this, function($this, result) {
        $this.withdrawTxs = [...$this.withdrawTxs, ...result.data];
        $this.isDisableWithdrawTxLoading = result.data.length < $this.pageSize;
      }, this.pageNoWithdrawTxs++, this.pageSizeWithdrawTxs);
    },
    onDepositTransactionLoad : function(isAppend = false) {
      if (this.user == null) return;
      transactionsProvider.getMyDepositTransactions(this, function($this, result) {
        $this.depositTxs = [...$this.depositTxs, ...result.data];
        $this.isDisableDepositTxLoading = result.data.length < $this.pageSize;
      }, this.pageNoDepositTxs++, this.pageSizeDepositTxs);
    },
    onWalletLoad : function() {
      if (this.user == null) return;

      walletsProvider.getMyWallet(this, function($this, result) {
        $this.wallet = result.data;
      });
    },
    isNew : function(date) {
      return utility.isNewContent(date);
    },
    readableDateString : function(date) {
      return "<span class='ml-2 mr-1 far fa-clock'></span>" + utility.readableDateString(date);
    }
  }
};


</script>