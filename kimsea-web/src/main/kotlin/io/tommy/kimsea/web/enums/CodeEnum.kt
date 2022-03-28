package io.tommy.kimsea.web.enums

enum class CodeEnum(private var code: String, var message: String) {
    SUCCESS("C0000", "성공하였습니다.")
    , FAIL("C1000", "요청 실패하였습니다.")
    , BAD_REQUEST("C1002", "잘못된 요청입니다.")
    , ACCESS_DENY("C1003","접근이 제한되었습니다.")
    , INVALID_FORMAT("C1013", "입력형식이 잘못되었습니다.")
    , USER_EXIST("C4001", "사용자가 이미 존재합니다.")
    , USER_NOT_EXIST("C4002", "사용자가 존재하지 않습니다.")
    , NOT_ALLOW_DESKTOP("C5000", "데스크탑에서 접근할수없습니다.")
    , NOT_USE_UPLOAD("C5001", "현재 이미지 업로드를 할수없습니다.")
    , NOT_EXIST_ASSET("C6001", "현재 NFT가 존해하지않습니다.")
    , INVALID_WITHDRAW_STATUS_ASSET("C6002", "현재 NFT는 출금할수없습니다. 판매중이거나, 출금NFT인지 확인해주세요.")
    , NOT_EXIST_WALLET("C7001", "현재 지갑이 존해하지않습니다.")
    , INVALID_ASSET_STATUS("C7002", "자산을 매매할수없습니다.")
    , ALREADY_SELL_SAME_PRICE("C7003", "수정된 판매가격이 현재 판매가격과 같습니다.")
    , SELL_UNDER_MIN_SELL_PRICE("C8001", "최소판매금보다 적습니다.")
    , NOT_AVAILABLE_PRICE_IN_WALLET("C8002", "지갑에 충분한 수량이 없습니다.")
    , NOT_EXIST_ORDER("C8003", "주문이 존재하지않습니다.")
    , NOT_SAME_ADDRESS_AND_WALLET("C8004", "지갑주소가 일치하지않습니다.")
    , WITHDRAW_FAIL("C8005", "출금이 실패하였습니다.")
    , WITHDRAW_CANCEL("C8006", "출금이 취소되었습니다.")
    , NOT_EXIST_TRANSACTION_RECEIPT("C8006", "트랜잭션이 존재하지않습니다.")
    , NOT_ENOUGH_WITHDRAW_FEE("C8007", "출금수수료가 모자랍니다.")
    , INVALID_ADDRESS("C8008", "잘못된 형식의 주소입니다.")
    , DO_NOT_WITHDRAW_MY_ADDRESS("C8009", "내 지갑으로는 출금할수없습니다.")
    , DO_NOT_TRADE_WITHDRAW_ASSET("C8010", "출금된 NFT는 거래할수없습니다.")
    , INVALID_APIKEY("C9001", "APIKEY가 올바르지않습니다.")
    , EXCEED_LIMIT_USAGE_APIKEY("C9002", "API사용량이 추과되었습니다.")
    , DISABLE_API("C9003", "API 사용신청을 해주세요.")
    , NOT_AVAILABLE_SERVICE("C9004", "현재 서비스를 이용할수없습니다.")
    ;

    fun getCode(): String {
        return code
    }

    fun setCode(code: String) {
        this.code = code
        message = valueOf(code).message
    }
}
