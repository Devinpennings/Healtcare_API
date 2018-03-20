package com.pharmacy.healthcare.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("doctor")
public class Doctor extends User implements Serializable{

    @Override
    public String getType() {
        return "doctor";
    }


}
