
package com.github.andrewthehan.etude.exception;

public class EtudeException extends RuntimeException{
  public EtudeException(){
    super();
  }

  public EtudeException(String message){
    super(message);
  }

  public EtudeException(String message, Throwable cause){
    super(message, cause);
  }

  public EtudeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public EtudeException(Throwable cause){
    super(cause);
  }
}