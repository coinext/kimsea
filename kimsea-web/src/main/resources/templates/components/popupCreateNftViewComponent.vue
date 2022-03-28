<script type="text/javascript">
const popupCreateNftViewComponent = {
  template: `<div id="createNftPopup">
  <div class="fix-m-4" role="document">
    <div class="modal-content">
      <div class="modal-body">
        <div class="row no-gutters">
          <div class="col-6 pl-lg-2 mb-3">
            <div class="card h-100 text-left">
              <div class="bg-light card-body">
                <h5>1) 카테고리</h5>
                <select class="mb-3 custom-select" v-model="category">
                  <option v-for="category in categories" v-if="category.name !='ALL'" :value="category.name" v-text="category.desc"></option>
                </select>
                <hr>
                <h5>2) NFT 이름</h5>
                <div class="form-group">
                  <input class="form-control" v-model="name" placeholder="이름을 입력하세요" type="text">
                </div>
                <hr>
                <h5>3) NFT 설명</h5>
                <div class="form-group">
                  <textarea class="form-control" rows="8" v-model="description" placeholder="설명을 입력하세요" type="text"/>
                </div>
                <div class="mb-3">
                  <a class="btn btn-falcon-default mb-1" data-toggle="collapse" href="#collapseAttr" role="button" aria-expanded="false" aria-controls="collapseAttr"><h5>속성 만들기 (옵션)</h5></a>
                  <div class="collapse" id="collapseAttr">
                    <div class="card h-100">
                      <div class="bg-light card-body">
                        <div class="form-row">
                          <div class="col-6 col-sm-6">
                            <div class="form-group">
                              <input class="form-control" v-model="attrs[0].trait_type" placeholder="속성명" type="text">
                            </div>
                          </div>
                          <div class="col-6 col-sm-6">
                            <div class="form-group">
                              <input class="form-control" v-model="attrs[0].value" placeholder="속성값" type="text">
                            </div>
                          </div>
                          <div class="col-6 col-sm-6">
                            <div class="form-group">
                              <input class="form-control" v-model="attrs[1].trait_type" placeholder="속성명" type="text">
                            </div>
                          </div>
                          <div class="col-6 col-sm-6">
                            <div class="form-group">
                              <input class="form-control" v-model="attrs[1].value" placeholder="속성값" type="text">
                            </div>
                          </div>
                        </div>
                        <div class="form-row">
                          <div class="col-6 col-sm-6">
                            <div class="form-group">
                              <input class="form-control" v-model="attrs[2].trait_type" placeholder="속성명" type="text">
                            </div>
                          </div>
                          <div class="col-6 col-sm-6">
                            <div class="form-group">
                              <input class="form-control" v-model="attrs[2].value" placeholder="속성값" type="text">
                            </div>
                          </div>
                          <div class="col-6 col-sm-6">
                            <div class="form-group">
                              <input class="form-control" v-model="attrs[3].trait_type" placeholder="속성명" type="text">
                            </div>
                          </div>
                          <div class="col-6 col-sm-6">
                            <div class="form-group">
                              <input class="form-control" v-model="attrs[3].value" placeholder="속성값" type="text">
                            </div>
                          </div>
                        </div>
                        <div class="form-row">
                          <div class="col-6 col-sm-6">
                            <div class="form-group">
                              <input class="form-control" v-model="attrs[4].trait_type" placeholder="속성명" type="text">
                            </div>
                          </div>
                          <div class="col-6 col-sm-6">
                            <div class="form-group">
                              <input class="form-control" v-model="attrs[4].value" placeholder="속성값" type="text">
                            </div>
                          </div>
                          <div class="col-6 col-sm-6">
                            <div class="form-group">
                              <input class="form-control" v-model="attrs[5].trait_type" placeholder="속성명" type="text">
                            </div>
                          </div>
                          <div class="col-6 col-sm-6">
                            <div class="form-group">
                              <input class="form-control" v-model="attrs[5].value" placeholder="속성값" type="text">
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-6 pl-lg-2 mb-3">
            <div class="card h-100 text-left">
                <div class="bg-light card-body">
                <h5>4) NFT 썸네일</h5>
                  <form method="POST" enctype="multipart/form-data" id="imageFileForm">
                    <div class="form-group">
                      <div class="col-12 align-content-center text-center">
                        <input type="file" id="attach-file1" name="file" accept="image/*" style="display: none;" v-on:change="onAddImageChange(1, $event)"/>
                        <img id="previewImg1Id" class="border border-dashed img-fluid w-72" src="./assets/img/readydocument.png" v-on:click="onAddImageClick(1, $event)">
                      </div>
                    </div>
                  </form>
                <p class="fs--1 text-600">썸네일 이미지를 올려주세요.(10MB이하)<br/>*추천 해상도 : 600x600px</p>
                <hr>
                <h5>5) NFT 파일</h5>
                  <div>
                    <form method="POST" enctype="multipart/form-data" id="attachFileForm">
                      <div class="form-group">
                        <div class="col-12 align-content-center text-center">
                          <input type="file" id="attach-file2" name="file" accept="model/gltf-binary,audio/*,image/*,video/*,.glb" style="display: none;" v-on:change="onAddImageChange(2, $event)"/>
                          <img id="previewImg2Id" class="border border-dashed img-fluid w-72" src="./assets/img/readydocument.png" v-on:click="onAddImageClick(2, $event)">
                        </div>
                      </div>
                    </form>
                    <p class="fs--1 text-600">에셋 파일을 올려주세요.(10MB이하)</p>
                    <div class="bg-300 p-2">
                      <span class="fs--1">*가능 파일</span><br/>
                      <span class="fs--2 ml-2">- AUDIO (mp3,ogg)</span><br/>
                      <span class="fs--2 ml-2">- VIDEO (mp4)</span><br/>
                      <span class="fs--2 ml-2">- IMAGE (png,jpg)</span><br/>
                      <span class="fs--2 ml-2">- 3D (glb,gltf)</span><br/>
                    </div>
                  </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button class="btn btn-secondary btn-sm" type="button" v-on:click="onClosePopupClick()">닫기</button>
        <button class="btn btn-danger btn-sm" type="button" v-on:click="onCreateNftClick()">NFT 만들기</button>
      </div>
    </div>
  </div>
  <div :class="{'wrap-loading':true, 'display-none':!isLoading}">
    <div class="spinner-border text-info text-center" role="status">
      <span class="sr-only">Loading...</span>
    </div>
  </div>
  </div>`
  , el : "#createNftPopup"
  , data : function() {
    return {
      user : _user,
      params : this.$route.params,
      isLoading : false,
      networkChains : _network_chains,
      categories: _categories,
      attachFileMime : "image/*",
      networkChain : _network_chains[0],
      category : "PHOTO",
      name : "",
      description : "",
      attrs : [{"trait_type":"", "value":""}, {"trait_type":"", "value":""}, {"trait_type":"", "value":""}, {"trait_type":"", "value":""}, {"trait_type":"", "value":""}, {"trait_type":"", "value":""}]
    }
  }
  , watch: {
      category : function(v) {
        this.attachFileMime = this.categories[v].mime;
      }
  }
  , mounted : function() {
  }
  , created: function() {

  }
  , methods : {
    onAddImageClick: function(index, e) {
      utility.onAddImageClick(index,e);
    },
    onAddImageChange : function(index, e) {
      utility.onAddImageChange(index,e);
    },
    onClosePopupClick : function() {
      swal.close();
    },
    onCreateNftClick : function() {
      const $this = this;

      if (this.name == null || this.name.length <= 0) {
        utility.errorAlert("", "이름을 입력해주세요.");
        return;
      }

      if (this.description == null || this.description.length <= 0) {
        utility.errorAlert("", "설명을 입력해주세요.");
        return;
      }

      if ($("#attach-file1")[0].files[0] == null) {
        utility.errorAlert("", "이미지를 첨부해주세요.");
        return;
      }

      if ($("#attach-file2")[0].files[0] == null) {
        utility.errorAlert("", "첨부파일을 첨부해주세요.");
        return;
      }

      utility.resizeImage(function(resizedFile) {
         const imageFile =  $("#attach-file1")[0].files[0];
         const prevImageFile = resizedFile;
         const attachFile =  $("#attach-file2")[0].files[0];

         const formData = new FormData();
         formData.append("prevImageFile", prevImageFile);
         formData.append("imageFile", imageFile);
         formData.append("attachFile", attachFile);
         formData.append('networkChain', $this.networkChain);
         formData.append('category', $this.category);
         formData.append('name', $this.name);
         formData.append('description', $this.description);
         formData.append('attrs', JSON.stringify($this.attrs));

         $this.isLoading = true;
         assetsProvider.createAsset($this, formData, function($this, result) {
           if (result.code == "C0000") {
             utility.successAlert("NFT 생성","생성에 성공하였습니다!", function() {
               $this.isLoading = false;
               window.location.href = "/home#/myassets/ALL";
             });
           } else {
             utility.errorAlert("NFT 생성 실패",result.code, result.msg, function() {
               $this.isLoading = false;
               $('#createNftPopup').modal('hide');
             });
           }
         });
      }, "attach-file1", "previewResizeImg1Id", 500, 500);
    }
  }
};
</script>