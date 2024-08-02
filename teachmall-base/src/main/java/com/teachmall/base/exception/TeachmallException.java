package com.teachmall.base.exception;

public class TeachmallException extends RuntimeException {

   private String errMessage;

   public TeachmallException() {
      super();
   }

   public TeachmallException(String errMessage) {
      super(errMessage);
      this.errMessage = errMessage;
   }

   public String getErrMessage() {
      return errMessage;
   }

   public static void cast(CommonError commonError){
       throw new TeachmallException(commonError.getErrMessage());
   }
   public static void cast(String errMessage){
       throw new TeachmallException(errMessage);
   }

}