package com.ashim.batch.ex.persist;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author ashimjk on 11/24/2018
 */
@Entity
public class ExampleEntity implements Serializable {

    private static final long serialVersionUID = 7210772180061547245L;

    @Id
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "increment")
    private long id;

    @Column
    private String exampleText;

    public ExampleEntity(String exampleText) {
        this.exampleText = exampleText;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((exampleText == null) ? 0 : exampleText.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ExampleEntity other = (ExampleEntity) obj;
        if (exampleText == null) {
            return other.exampleText == null;
        } else
            return exampleText.equals(other.exampleText);
    }
}
