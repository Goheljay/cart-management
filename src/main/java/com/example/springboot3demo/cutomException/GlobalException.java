package com.example.springboot3demo.cutomException;//package com.cupon.cuponsmanagement.cutomException;
//
//import java.util.Date;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//
//@ControllerAdvice
//public class GlobalException {
//
//	@ExceptionHandler(ResourceNotFound.class)
//	public ResponseEntity<?> resourceNotFoundHandling(ResourceNotFound exception, WebRequest request){
//		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
//		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
//	}
//
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<?> globalExceptionHandling(Exception exception, WebRequest request){
//		ErrorDetails errorDetails =
//				new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
//		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
//	}
//
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<ErrorDetails> handleEntitiyException(MethodArgumentNotValidException ex){
//
//		ErrorDetails ValidationError = new ErrorDetails("validation error",
//				ex.getBindingResult().getFieldError().getDefaultMessage(),HttpStatus.BAD_REQUEST);
//
//		return new ResponseEntity<ErrorDetails>(ValidationError, HttpStatus.BAD_REQUEST);
//
//
//	}
//
//}