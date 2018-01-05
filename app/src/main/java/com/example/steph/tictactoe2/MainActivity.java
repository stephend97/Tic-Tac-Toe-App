package com.example.steph.tictactoe2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.example.steph.tictactoe2.MainActivity.Status.EMPTY;
import static com.example.steph.tictactoe2.MainActivity.Status.PLAYER1;
import static com.example.steph.tictactoe2.MainActivity.Status.PLAYER2;

public class MainActivity extends AppCompatActivity {

    public enum Status{
        PLAYER1,
        PLAYER2,
        EMPTY
    };

    Status active_player = PLAYER1;

    boolean game_is_active = true;

    // size 9
    Status[] score_board = {EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,
            EMPTY,EMPTY,EMPTY,EMPTY};

    //all the possible winning spots
    int[][] win_positions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6},
            {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    //when one of the squares gets clicked on
    public void click_func(View view){
        ImageView img = (ImageView) view;

        int i = Integer.parseInt(img.getTag().toString());

        if(EMPTY == score_board[i] && game_is_active){

            score_board[i] = active_player;

            img.setTranslationY(-1000f);

            if(PLAYER1 == active_player){
                img.setImageResource(R.drawable.red_piece);
                active_player = PLAYER2;
            }
            else if(PLAYER2.equals(active_player)) {
                img.setImageResource(R.drawable.yellow_piece);
                active_player = PLAYER1;
            }

            img.animate().translationYBy(1000f).setDuration(200);

            TextView winner_text = (TextView) findViewById(R.id.winner_text);

            LinearLayout layout = (LinearLayout) findViewById(R.id.linear_layout);

            for (int[] win_position : win_positions){
                if(score_board[win_position[0]] == score_board[win_position[1]] &&
                        score_board[win_position[1]] == score_board[win_position[2]] &&
                        score_board[win_position[0]] != EMPTY){

                    //winner is declared
                    game_is_active = false;

                    if (score_board[win_position[0]] == PLAYER1){
                        winner_text.setText("Red Won!");
                    }
                    else{
                        winner_text.setText("Yellow Won!");
                    }

                    layout.setVisibility(View.VISIBLE);

                }
                else{
                    boolean game_is_over = true;

                    //checks if the game is a draw
                    for(Status count : score_board){
                        if(EMPTY == count){
                            game_is_over = false;
                        }
                    }

                    if (game_is_over) {
                        winner_text.setText("It's a Draw!");
                        layout.setVisibility(View.VISIBLE);
                    }
                }
            }//end of for loop
        }
    }

    //resets game
    public void play_again(View view){
        LinearLayout layout = (LinearLayout) findViewById(R.id.linear_layout);

        //takes the play again window away
        layout.setVisibility(View.INVISIBLE);

        game_is_active = true;

        active_player = PLAYER1;

        //resets the score_board array
        for(int i = 0; i < score_board.length; i++){
            score_board[i] = EMPTY;
        }

        GridLayout grid_layout = (GridLayout) findViewById(R.id.grid_layout);

        //takes away image for each square
        for(int i = 0; i < grid_layout.getChildCount(); i++){
            ((ImageView) grid_layout.getChildAt(i)).setImageResource(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}