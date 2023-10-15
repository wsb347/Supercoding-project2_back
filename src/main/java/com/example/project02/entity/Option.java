//package com.example.project02.entity;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//public class Option {
//    @Id
//    @Column(name = "option_id")
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "product_id", referencedColumnName = "id")
//    private Product product;
//
//    @Column(name = "option_name")
//    private String optionName;
//
//    @Column(name = "option_price")
//    private double OptionPrice;
//}
