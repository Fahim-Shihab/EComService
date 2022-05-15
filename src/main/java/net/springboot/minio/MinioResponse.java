package net.springboot.minio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.springboot.common.base.ServiceResponse;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MinioResponse extends ServiceResponse implements Serializable {
    private byte[] file;

    public MinioResponse(boolean code, String message) {
        super(code, message);
    }

    public MinioResponse(boolean code, String message, byte[] file) {
        super(code, message);
        this.file = file;
    }
}
