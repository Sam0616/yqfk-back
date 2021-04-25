package com.ly.bigdata.vo;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MenuVo {
    private Integer id;
    private String name;
    private String path;
    private String icon;
    private List<MenuVo> children;
}
