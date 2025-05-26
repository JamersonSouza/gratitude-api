package tech.jamersondev.gratitude.core.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import tech.jamersondev.gratitude.core.enums.CardTypeEnum;
import tech.jamersondev.gratitude.payload.form.UpdateCardForm;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "tb_card")
public class Card extends CoreEntity {

    @Enumerated(EnumType.STRING)
    private CardTypeEnum cardTypeEnum;

    private String text;

    private String color;

    private boolean isFavorite;

    private Date createdDate;

    private Date updatedDate;

    @ManyToOne
    private User user;

    public Card(CardTypeEnum cardTypeEnum, String color, String text, User user) {
        this.cardTypeEnum = cardTypeEnum;
        this.color = color != null ? color : "#4CAFAD";
        this.text = text;
        this.user = user;
        this.createdDate = new Date();
        this.isFavorite = false;
        setIdentifier(UUID.randomUUID());
    }

    public void updateCard(UpdateCardForm form) {
        this.text = form.text();
        this.color = form.color();
        this.cardTypeEnum = form.cardType();
        this.updatedDate = new Date();
    }

    public Card() {
    }

    public CardTypeEnum getCardTypeEnum() {
        return cardTypeEnum;
    }

    public void setCardTypeEnum(CardTypeEnum cardTypeEnum) {
        this.cardTypeEnum = cardTypeEnum;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
