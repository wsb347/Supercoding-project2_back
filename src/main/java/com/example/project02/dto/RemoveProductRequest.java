package com.example.project02.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class RemoveProductRequest {

    private List<Long> cartProductIdList;
}
