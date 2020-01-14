package todobackend;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.Objects;

@Entity
public class Todo {

    @GeneratedValue
    @Id
    private Long id;
    private String title;
    private Boolean completed;
    private Integer sortOrder;

    public Todo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return String.format("/%d", id);
    }

    @JsonProperty(required = true)
    public void setTitle(String title) {
        this.title = title;
    }


    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    @JsonProperty("order")
    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return getId() == todo.getId() &&
                getTitle().equals(todo.getTitle()) &&
                getCompleted().equals(todo.getCompleted()) &&
                getSortOrder().equals(todo.getSortOrder());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getCompleted(), getSortOrder());
    }
}
