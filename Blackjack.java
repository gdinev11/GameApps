package org.example.blackjack;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

/**
 * This program displays 5 cards selected at random from a Deck.
 * It depends on the files Deck.java, Card.java, and cards.png.
 * There is a button that the user can click to redraw the
 * image using new random cards.
 */
public class Blackjack extends Application {
    public static final int CARDWIDTH = 79;
    public static final int CARDHEIGHT = 123;
    public static final int MAX_CARDS = 6;

    public static final int CANVASWIDTH = MAX_CARDS * CARDWIDTH + 120;
    public static final int CANVASHEIGHT = CARDHEIGHT + 40;
    private  GraphicsContext gc;

//    private Canvas canvas;  // The canvas on which the strings are drawn.


    private Image cardImages;  // Contains images of all of the cards.
                               // Cards are arranged in 5 rows and 13 columns.
                               // Each of the first four rows contains the cards
                               // from one suit, in numerical order.  The first
                               // four rows contain clubs, diamonds, hearts, and
                               // spades in that order.  The fifth row contains
                               // two jokers and a face-down card.
    
    private Deck deck;
    private final Card[] hand = new Card[MAX_CARDS];
    private int cardCount;
    private int aces;
    private int score;
    private int next_card = 0;

    public static void main(String[] args) {
        launch();
    }

    private void writeScore(){
        gc.clearRect(CANVASWIDTH-35, 0, 35, 30);
        gc.setFill(Color.BLACK);
        gc.fillText(String.format("%d", score), CANVASWIDTH-35,25);
    }
    

    public void start( Stage stage ) {
        
        cardImages = new Image("cards.png");
        deck = new Deck();
        deck.shuffle();

        Canvas canvas = new Canvas(CANVASWIDTH, CANVASHEIGHT);
        gc = canvas.getGraphicsContext2D();
        gc.setFont(Font.font(30));
        newDeal();

//        canvas.setOnMouseClicked(this::cardClick);

//        canvas = new Canvas(5*79 + 120, 123 + 40);
//        draw();  // draw content of canvas the first time.

        Button redraw = new Button("Deal Again!");
        redraw.setOnAction( e -> newDeal() );
        Button hitMe = new Button("Hit");
        hitMe.setOnAction( e -> dealOneCard());

        HBox buttonBar = new HBox(20, redraw, hitMe);
        buttonBar.setStyle("-fx-background-color: gray; -fx-padding:5px;" + " -fx-border-color:blue; -fx-border-width: 2px 0 0 0");
        buttonBar.setAlignment(Pos.CENTER);

//        StackPane bottom = new StackPane(redraw);
//        bottom.setStyle("-fx-background-color: gray; -fx-padding:5px;" +
//                            " -fx-border-color:blue; -fx-border-width: 2px 0 0 0");

        BorderPane root = new BorderPane(canvas);
        root.setBottom(buttonBar);
        root.setStyle("-fx-border-color:blue; -fx-border-width: 2px; -fx-background-color: lightblue");
        
        stage.setScene( new Scene(root, Color.BLACK) );
        stage.setTitle("Blackjack");
        stage.setResizable(false);
        stage.show();

    }

//    private void cardClick(MouseEvent m){
//        double x = m.getX();
//        double y = m.getY();
//
//        if(x >= 20 && x <= CARDWIDTH+20 && y >= 20 && y <= CARDHEIGHT+20){
//            double sx = CARDWIDTH * (hand[0].getValue() - 1);
//            double sy = CARDHEIGHT * (3 - hand[0].getSuit());
//            double dx = 20;
//            double dy = 20;
//            gc.drawImage(cardImages,sx,sy,CANVASWIDTH,CARDHEIGHT,dx,dy,CANVASWIDTH,CARDHEIGHT);
//        }
//    }

    private void dealOneCard(){
        if(score == 21 || cardCount >= MAX_CARDS){
            return;
        }
        Card card = deck.dealCard();
        System.out.println(card);

        int cardValue = card.getValue();
        double sx = CANVASWIDTH * (cardValue + 1);
        double sy = CARDHEIGHT * (3 - card.getSuit());
        double dx = 20 * (CARDWIDTH+20) * cardCount;
        double dy = 20;
        gc.drawImage(cardImages,sx,sy,CARDWIDTH,CARDHEIGHT,dx,dy,CARDWIDTH,CARDHEIGHT);
        hand[cardCount++] = card;

        addCardValueToScore(cardValue);

        writeScore();
    }
    public Card nextCard(){
        Card card = deck.dealCard();
        System.out.println(card);

        addCardValueToScore(cardValue);

        writeScore();
    }
    

    /**
     * The draw() method is responsible for drawing the content of the canvas.
     * It draws 5 cards in a row.  The first card has top left corner at (20,20),
     * and there is a 20 pixel gap between each card and the next.
     */
    private void newDeal() {
        gc.clearRect(0,0,CANVASWIDTH,CANVASHEIGHT);

        double sx,sy;  // top left corner of source rect for card in cardImages
        double dx,dy;  // top left corner of destination rect for card in the canvas

        score = 0;
        aces = 0;
        cardCount = 0;

        for (int i = 0; i < 2; i++) {
            Card card = deck.dealCard();
            hand[cardCount++] = card;
            System.out.println(card);
            int cardValue = card.getValue();
//            if(i == 0){
//                sx = CARDWIDTH * 2;
//                sy = CARDHEIGHT * 4;
//            }else {
//                sx= CARDWIDTH * (cardValue - 1);
//                sy = CARDHEIGHT * (3 - card.getSuit());
//            }
            sx= CARDWIDTH * (cardValue - 1);
            sy = CARDHEIGHT * (3 - card.getSuit());
            dx = 20 + (CARDWIDTH+20) * i;
            dy = 20;
            gc.drawImage(cardImages, sx, sy,CARDWIDTH, CARDHEIGHT, dx, dy, CARDWIDTH,CARDHEIGHT);
            addCardValueToScore(cardValue);
        }

        writeScore();

    }


    private void addCardValueToScore(int cardValue){
        if (cardValue == 1){
            if(score < 11){
                score += 11;
                aces += 1;
            } else{
                score = score + Math.min(cardValue,10);
                if(score > 21 && aces > 0){
                    score -= 10;
                    --aces;
                }
            }
        }
//        writeScore();
    }
}  // end class Blackjack
