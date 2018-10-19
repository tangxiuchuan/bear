package org.bear.reuslt;

public class ResultUtils {

    /**
     * 返回成功  不带返回结果
     * @return
     */
    public static Result buildSuccess(){
        Result result=new Result();
        result.setStatus("success");
        return result;
    }

    /**
     * 返回成功  带返回结果
     * @param data
     * @return
     */
    public static Result buildSuccess(Object data){
        Result result=new Result();
        result.setStatus("success");
        result.setData(data);
        return result;
    }


    /**
     * 返回失败信息
     * @param message  失败的具体原因
     * @param code    失败的编码
     * @return
     */
    public static Result  buildFail(String message,Integer code){
        Result result=new Result();
        result.setStatus("fail");
        result.setCode(code);
        result.setMessage(message);
        return result;
    }


}
