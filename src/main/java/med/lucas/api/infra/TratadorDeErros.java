package med.lucas.api.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException exception) {
        var erros = exception.getFieldErrors();

        //;
        //é necessário converter a lista de erros para uma lista de DadosErrosValidação (para capturar apenas os erros desejados)
        //Então, para convertermos de uma lista para outra, chamamos os recursos do Java8 - erros.stream().map(Dto desejado),
        //adicionar - ::new - para chamar o construtor e no final adicionar - .toList() - para converter para uma lista

        return ResponseEntity.badRequest().body(erros.stream().map(DadosErro400::new).toList());
    }


    //================================================================================================//
    //                                           DTOs                                                 //
    //================================================================================================//
    private record DadosErro400(String campo, String mensagem) {
        public DadosErro400(FieldError fieldError) {
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}
