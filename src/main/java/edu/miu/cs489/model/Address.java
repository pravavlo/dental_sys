package edu.miu.cs489.model;
import jakarta.persistence.*;
import lombok.*;


    @Entity
    @Table(name = "addresses")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Address {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String street;
        private String city;
        private String state;
        private String zipCode;
        private String country;
    }

