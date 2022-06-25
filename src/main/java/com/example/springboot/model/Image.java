package com.example.springboot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.Entity;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Image extends BaseObject{
    private int height;
    private int width;
    private int quality;
    private ScaleType scaleType;
    private String fillColor;
    private Type type;
}
