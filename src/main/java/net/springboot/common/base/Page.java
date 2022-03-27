package net.springboot.common.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

@Getter
@Setter
public class Page implements Serializable {
    private int page;
    private int size;

    public void checkValues(){
        if (size <= 0) {
            size = Defs.DEFAULT_LIMIT;
        }
        if (page <= 0) {
            page = Defs.DEFAULT_PAGE;
        }
        page = page*size;
    }
}
