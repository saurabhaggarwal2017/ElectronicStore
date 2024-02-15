package com.lcwd.electronic.store.helper;

import com.lcwd.electronic.store.dto.PageableResponse;
import com.lcwd.electronic.store.dto.UserDto;
import com.lcwd.electronic.store.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {
    public static <U,V>PageableResponse<V> getPageableResponse(Page<U>pages,Class<V> dto){

        List<U> entities = pages.getContent();

        List<V> dtoList = entities.stream().map(entity -> new ModelMapper().map(entity,dto)).collect(Collectors.toList());

        PageableResponse<V> response = new PageableResponse<>();
        response.setContent(dtoList);
        response.setPageNumber(pages.getNumber());
        response.setPageSize(pages.getSize());
        response.setTotalElements(pages.getTotalElements());
        response.setTotalPages(pages.getTotalPages());
        response.setNumberOfElementsInPage(pages.getNumberOfElements());
        response.setLastPage(pages.isLast());

        return response;
    }
}
