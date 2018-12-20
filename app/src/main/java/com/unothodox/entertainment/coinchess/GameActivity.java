package com.unothodox.entertainment.coinchess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    static class boardPiece    {
        int BG;
        int piece;
    }

    /*
    * odd = black pieces
    * even = white pieces

    piece identity number   -   piece
    ---------------------------------
                        00  -   empty = no piece
                        01  -   black king
                        02  -   white king
                        11  -   black rook
                        12  -   white rook
                        21  -   black knight
                        22  -   white knight
                        31  -   black bishop
                        32  -   white bishop
                        41  -   black queen
                        42  -   white queen
                        51  -   black pawn
                        52  -   white pawn
     */

    boardPiece[][] boardConfig;
    GridView gv_board;
    TextView tv_moves;

    static class piecePosition  {
        int x;
        int y;
    }

    piecePosition selected;
    boolean whiteTurn = true;
    int moves = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        boardConfig = new boardPiece[8][8];
        selected = new piecePosition();
        gv_board = findViewById(R.id.gv_board);
        tv_moves = findViewById(R.id.tv_moves);

        setInitialBoardSetting();
        setBoard();

        gv_board.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                boardPiece p = boardConfig[i%8][i/8];

                if (p.BG == 1)  {
                    movePiece(i%8, i/8);
                }else   {
                    if (p.piece == 0)
                        noPossibleMovements();
                    else    {
                        if ((whiteTurn && p.piece%2==0) || (!whiteTurn && p.piece%2==1))    {
                            if (selected.x == i%8 && selected.y == i/8)
                                noPossibleMovements();
                            else {
                                possibleMovements(p.piece, i%8, i/8);
                            }
                        }else
                            noPossibleMovements();
                    }
                }

                setBoard();
            }
        });
    }

    void setInitialBoardSetting()   {
        for (int j=0; j<8; j++)  {
            for (int i=0; i<8; i++) {
                boardConfig[i][j] = new boardPiece();
                boardConfig[i][j].BG = 0;
                if (j==1)
                    boardConfig[i][j].piece = 51;
                else if (j==6)
                    boardConfig[i][j].piece = 52;
                else
                    boardConfig[i][j].piece = 0;
            }
        }
        boardConfig[0][0].piece = 11;
        boardConfig[0][7].piece = 12;
        boardConfig[1][0].piece = 21;
        boardConfig[1][7].piece = 22;
        boardConfig[2][0].piece = 31;
        boardConfig[2][7].piece = 32;
        boardConfig[3][0].piece = 41;
        boardConfig[3][7].piece = 42;
        boardConfig[4][0].piece = 1;
        boardConfig[4][7].piece = 2;
        boardConfig[5][0].piece = 31;
        boardConfig[5][7].piece = 32;
        boardConfig[6][0].piece = 21;
        boardConfig[6][7].piece = 22;
        boardConfig[7][0].piece = 11;
        boardConfig[7][7].piece = 12;

        selected.x = -1;
        selected.y = -1;
    }

    void setBoard()  {
        ArrayAdapter adapter = new BoardPieceLayout(this, getBoardPiecesArray(boardConfig));
        gv_board.setAdapter(adapter);

        tv_moves.setText(String.valueOf(moves));
    }

    private ArrayList<boardPiece> getBoardPiecesArray(boardPiece[][] e)   {
        ArrayList<boardPiece> r = new ArrayList<>();

        for (int j=0; j<8; j++)  {
            for (int i=0; i<8; i++) {
                r.add(e[i][j]);
            }
        }

        return r;
    }

    private void possibleMovements(int type, int i, int j)    {
        noPossibleMovements();
        selected.x = i;
        selected.y = j;

        if (type/10 == 0) {
            //king
            if (i>0 && j>0)
                if ((whiteTurn && 
                        (boardConfig[i-1][j-1].piece%2 != 0 || 
                                boardConfig[i-1][j-1].piece == 0)) || 
                        (!whiteTurn && boardConfig[i-1][j-1].piece%2 != 1))
                    boardConfig[i-1][j-1].BG = 1;
            if (j>0)
                if ((whiteTurn &&
                        (boardConfig[i][j-1].piece%2 != 0 ||
                                boardConfig[i][j-1].piece == 0)) ||
                        (!whiteTurn && boardConfig[i][j-1].piece%2 != 1))
                    boardConfig[i][j-1].BG = 1;
            if (i<7 && j>0)
                if ((whiteTurn &&
                        (boardConfig[i+1][j-1].piece%2 != 0 ||
                                boardConfig[i+1][j-1].piece == 0)) ||
                        (!whiteTurn && boardConfig[i+1][j-1].piece%2 != 1))
                    boardConfig[i+1][j-1].BG = 1;
            if (i>0)
                if ((whiteTurn &&
                        (boardConfig[i-1][j].piece%2 != 0 ||
                                boardConfig[i-1][j].piece == 0)) ||
                        (!whiteTurn && boardConfig[i-1][j].piece%2 != 1))
                    boardConfig[i-1][j].BG = 1;
            if (i<7)
                if ((whiteTurn &&
                        (boardConfig[i+1][j].piece%2 != 0 ||
                                boardConfig[i+1][j].piece == 0)) ||
                        (!whiteTurn && boardConfig[i+1][j].piece%2 != 1))
                    boardConfig[i+1][j].BG = 1;
            if (i>0 && j<7)
                if ((whiteTurn &&
                        (boardConfig[i-1][j+1].piece%2 != 0 ||
                                boardConfig[i-1][j+1].piece == 0)) ||
                        (!whiteTurn && boardConfig[i-1][j+1].piece%2 != 1))
                    boardConfig[i-1][j+1].BG = 1;
            if (j<7)
                if ((whiteTurn &&
                        (boardConfig[i][j+1].piece%2 != 0 ||
                                boardConfig[i][j+1].piece == 0)) ||
                        (!whiteTurn && boardConfig[i][j+1].piece%2 != 1))
                    boardConfig[i][j+1].BG = 1;
            if (i<7 && j<7)
                if ((whiteTurn &&
                        (boardConfig[i+1][j+1].piece%2 != 0 ||
                                boardConfig[i+1][j+1].piece == 0)) ||
                        (!whiteTurn && boardConfig[i+1][j+1].piece%2 != 1))
                    boardConfig[i+1][j+1].BG = 1;
        }else if (type/10 == 1) {
            //rook
            for (int ii=i+1; ii<8; ii++) {
                if ((whiteTurn &&
                        boardConfig[ii][j].piece%2 == 0 &&
                        boardConfig[ii][j].piece != 0) ||
                        (!whiteTurn &&
                                boardConfig[ii][j].piece%2 == 1))
                    break;
                else if ((whiteTurn &&
                        boardConfig[ii][j].piece%2 == 1) ||
                        (!whiteTurn &&
                                boardConfig[ii][j].piece%2 == 0 &&
                                boardConfig[ii][j].piece != 0)) {
                    boardConfig[ii][j].BG = 1;
                    break;
                }else
                    boardConfig[ii][j].BG = 1;
            }
            for (int ii=i-1; ii>-1; ii--) {
                if ((whiteTurn &&
                        boardConfig[ii][j].piece%2 == 0 &&
                        boardConfig[ii][j].piece != 0) ||
                        (!whiteTurn &&
                                boardConfig[ii][j].piece%2 == 1))
                    break;
                else if ((whiteTurn &&
                        boardConfig[ii][j].piece%2 == 1) ||
                        (!whiteTurn &&
                                boardConfig[ii][j].piece%2 == 0 &&
                                boardConfig[ii][j].piece != 0)) {
                    boardConfig[ii][j].BG = 1;
                    break;
                }else
                    boardConfig[ii][j].BG = 1;
            }
            for (int jj=j+1; jj<8; jj++) {
                if ((whiteTurn &&
                        boardConfig[i][jj].piece%2 == 0 &&
                        boardConfig[i][jj].piece != 0) ||
                        (!whiteTurn &&
                                boardConfig[i][jj].piece%2 == 1))
                    break;
                else if ((whiteTurn &&
                        boardConfig[i][jj].piece%2 == 1) ||
                        (!whiteTurn &&
                                boardConfig[i][jj].piece%2 == 0 &&
                                boardConfig[i][jj].piece != 0)) {
                    boardConfig[i][jj].BG = 1;
                    break;
                }else
                    boardConfig[i][jj].BG = 1;
            }
            for (int jj=j-1; jj>-1; jj--) {
                if ((whiteTurn &&
                        boardConfig[i][jj].piece%2 == 0 &&
                        boardConfig[i][jj].piece != 0) ||
                        (!whiteTurn &&
                                boardConfig[i][jj].piece%2 == 1))
                    break;
                else if ((whiteTurn &&
                        boardConfig[i][jj].piece%2 == 1) ||
                        (!whiteTurn &&
                                boardConfig[i][jj].piece%2 == 0 &&
                                boardConfig[i][jj].piece != 0))  {
                    boardConfig[i][jj].BG = 1;
                    break;
                }else
                    boardConfig[i][jj].BG = 1;
            }
        }else if (type/10 == 2) {
            //knight
            if (i>0 && j>1)
                if ((whiteTurn &&
                        (boardConfig[i-1][j-2].piece%2 != 0 ||
                                boardConfig[i-1][j-2].piece == 0)) ||
                        (!whiteTurn && boardConfig[i-1][j-2].piece%2 != 1))
                    boardConfig[i-1][j-2].BG = 1;
            if (i<7 && j>1)
                if ((whiteTurn &&
                        (boardConfig[i+1][j-2].piece%2 != 0 ||
                                boardConfig[i+1][j-2].piece == 0)) ||
                        (!whiteTurn && boardConfig[i+1][j-2].piece%2 != 1))
                    boardConfig[i+1][j-2].BG = 1;
            if (i>1 && j>0)
                if ((whiteTurn &&
                        (boardConfig[i-2][j-1].piece%2 != 0 ||
                                boardConfig[i-2][j-1].piece == 0)) ||
                        (!whiteTurn && boardConfig[i-2][j-1].piece%2 != 1))
                    boardConfig[i-2][j-1].BG = 1;
            if (i<6 && j>0)
                if ((whiteTurn &&
                        (boardConfig[i+2][j-1].piece%2 != 0 ||
                                boardConfig[i+2][j-1].piece == 0)) ||
                        (!whiteTurn && boardConfig[i+2][j-1].piece%2 != 1))
                    boardConfig[i+2][j-1].BG = 1;
            if (i>1 && j<7)
                if ((whiteTurn &&
                        (boardConfig[i-2][j+1].piece%2 != 0 ||
                                boardConfig[i-2][j+1].piece == 0)) ||
                        (!whiteTurn && boardConfig[i-2][j+1].piece%2 != 1))
                    boardConfig[i-2][j+1].BG = 1;
            if (i<6 && j<7)
                if ((whiteTurn &&
                        (boardConfig[i+2][j+1].piece%2 != 0 ||
                                boardConfig[i+2][j+1].piece == 0)) ||
                        (!whiteTurn && boardConfig[i+2][j+1].piece%2 != 1))
                    boardConfig[i+2][j+1].BG = 1;
            if (i>0 && j<6)
                if ((whiteTurn &&
                        (boardConfig[i-1][j+2].piece%2 != 0 ||
                                boardConfig[i-1][j+2].piece == 0)) ||
                        (!whiteTurn && boardConfig[i-1][j+2].piece%2 != 1))
                    boardConfig[i-1][j+2].BG = 1;
            if (i<7 && j<6)
                if ((whiteTurn &&
                        (boardConfig[i+1][j+2].piece%2 != 0 ||
                                boardConfig[i+1][j+2].piece == 0)) ||
                        (!whiteTurn && boardConfig[i+1][j+2].piece%2 != 1))
                    boardConfig[i+1][j+2].BG = 1;
        }else if (type/10 == 3){
            //bishop
            for (int ii=i+1, jj=j-1; ii<8 && jj>-1; ii++, jj--) {
                if ((whiteTurn &&
                        boardConfig[ii][jj].piece%2 == 0 &&
                        boardConfig[ii][jj].piece != 0) ||
                        (!whiteTurn &&
                                boardConfig[ii][jj].piece%2 == 1))
                    break;
                else if ((whiteTurn &&
                        boardConfig[ii][jj].piece%2 == 1) ||
                        (!whiteTurn &&
                                boardConfig[ii][jj].piece%2 == 0 &&
                                boardConfig[ii][jj].piece != 0))  {
                    boardConfig[ii][jj].BG = 1;
                    break;
                }else
                    boardConfig[ii][jj].BG = 1;
            }
            for (int ii=i-1, jj=j-1; ii>-1 && jj>-1; ii--, jj--) {
                if ((whiteTurn &&
                        boardConfig[ii][jj].piece%2 == 0 &&
                        boardConfig[ii][jj].piece != 0) ||
                        (!whiteTurn &&
                                boardConfig[ii][jj].piece%2 == 1))
                    break;
                else if ((whiteTurn &&
                        boardConfig[ii][jj].piece%2 == 1) ||
                        (!whiteTurn &&
                                boardConfig[ii][jj].piece%2 == 0 &&
                                boardConfig[ii][jj].piece != 0)) {
                    boardConfig[ii][jj].BG = 1;
                    break;
                }else
                    boardConfig[ii][jj].BG = 1;
            }
            for (int ii=i+1, jj=j+1; ii<8 && jj<8; ii++, jj++) {
                if ((whiteTurn &&
                        boardConfig[ii][jj].piece%2 == 0 &&
                        boardConfig[ii][jj].piece != 0) ||
                        (!whiteTurn &&
                                boardConfig[ii][jj].piece%2 == 1))
                    break;
                else if ((whiteTurn &&
                        boardConfig[ii][jj].piece%2 == 1) ||
                        (!whiteTurn &&
                                boardConfig[ii][jj].piece%2 == 0 &&
                                boardConfig[ii][jj].piece != 0)) {
                    boardConfig[ii][jj].BG = 1;
                    break;
                }else
                    boardConfig[ii][jj].BG = 1;
            }
            for (int ii=i-1, jj=j+1; ii>-1 && jj<8; ii--, jj++) {
                if ((whiteTurn &&
                        boardConfig[ii][jj].piece%2 == 0 &&
                        boardConfig[ii][jj].piece != 0) ||
                        (!whiteTurn &&
                                boardConfig[ii][jj].piece%2 == 1))
                    break;
                else if ((whiteTurn &&
                        boardConfig[ii][jj].piece%2 == 1) ||
                        (!whiteTurn &&
                                boardConfig[ii][jj].piece%2 == 0 &&
                                boardConfig[ii][jj].piece != 0)) {
                    boardConfig[ii][jj].BG = 1;
                    break;
                }else
                    boardConfig[ii][jj].BG = 1;
            }
        }else if (type/10 == 4) {
            //queen
            for (int ii=i+1; ii<8; ii++) {
                if ((whiteTurn &&
                        boardConfig[ii][j].piece%2 == 0 &&
                        boardConfig[ii][j].piece != 0) ||
                        (!whiteTurn &&
                                boardConfig[ii][j].piece%2 == 1))
                    break;
                else if ((whiteTurn &&
                        boardConfig[ii][j].piece%2 == 1) ||
                        (!whiteTurn &&
                                boardConfig[ii][j].piece%2 == 0 &&
                                boardConfig[ii][j].piece != 0)) {
                    boardConfig[ii][j].BG = 1;
                    break;
                }else
                    boardConfig[ii][j].BG = 1;
            }
            for (int ii=i-1; ii>-1; ii--) {
                if ((whiteTurn &&
                        boardConfig[ii][j].piece%2 == 0 &&
                        boardConfig[ii][j].piece != 0) ||
                        (!whiteTurn &&
                                boardConfig[ii][j].piece%2 == 1))
                    break;
                else if ((whiteTurn &&
                        boardConfig[ii][j].piece%2 == 1) ||
                        (!whiteTurn &&
                                boardConfig[ii][j].piece%2 == 0 &&
                                boardConfig[ii][j].piece != 0)) {
                    boardConfig[ii][j].BG = 1;
                    break;
                }else
                    boardConfig[ii][j].BG = 1;
            }
            for (int jj=j+1; jj<8; jj++) {
                if ((whiteTurn &&
                        boardConfig[i][jj].piece%2 == 0 &&
                        boardConfig[i][jj].piece != 0) ||
                        (!whiteTurn &&
                                boardConfig[i][jj].piece%2 == 1))
                    break;
                else if ((whiteTurn &&
                        boardConfig[i][jj].piece%2 == 1) ||
                        (!whiteTurn &&
                                boardConfig[i][jj].piece%2 == 0 &&
                                boardConfig[i][jj].piece != 0)) {
                    boardConfig[i][jj].BG = 1;
                    break;
                }else
                    boardConfig[i][jj].BG = 1;
            }
            for (int jj=j-1; jj>-1; jj--) {
                if ((whiteTurn &&
                        boardConfig[i][jj].piece%2 == 0 &&
                        boardConfig[i][jj].piece != 0) ||
                        (!whiteTurn &&
                                boardConfig[i][jj].piece%2 == 1))
                    break;
                else if ((whiteTurn &&
                        boardConfig[i][jj].piece%2 == 1) ||
                        (!whiteTurn &&
                                boardConfig[i][jj].piece%2 == 0 &&
                                boardConfig[i][jj].piece != 0))  {
                    boardConfig[i][jj].BG = 1;
                    break;
                }else
                    boardConfig[i][jj].BG = 1;
            }
            for (int ii=i+1, jj=j-1; ii<8 && jj>-1; ii++, jj--) {
                if ((whiteTurn &&
                        boardConfig[ii][jj].piece%2 == 0 &&
                        boardConfig[ii][jj].piece != 0) ||
                        (!whiteTurn &&
                                boardConfig[ii][jj].piece%2 == 1))
                    break;
                else if ((whiteTurn &&
                        boardConfig[ii][jj].piece%2 == 1) ||
                        (!whiteTurn &&
                                boardConfig[ii][jj].piece%2 == 0 &&
                                boardConfig[ii][jj].piece != 0))  {
                    boardConfig[ii][jj].BG = 1;
                    break;
                }else
                    boardConfig[ii][jj].BG = 1;
            }
            for (int ii=i-1, jj=j-1; ii>-1 && jj>-1; ii--, jj--) {
                if ((whiteTurn &&
                        boardConfig[ii][jj].piece%2 == 0 &&
                        boardConfig[ii][jj].piece != 0) ||
                        (!whiteTurn &&
                                boardConfig[ii][jj].piece%2 == 1))
                    break;
                else if ((whiteTurn &&
                        boardConfig[ii][jj].piece%2 == 1) ||
                        (!whiteTurn &&
                                boardConfig[ii][jj].piece%2 == 0 &&
                                boardConfig[ii][jj].piece != 0)) {
                    boardConfig[ii][jj].BG = 1;
                    break;
                }else
                    boardConfig[ii][jj].BG = 1;
            }
            for (int ii=i+1, jj=j+1; ii<8 && jj<8; ii++, jj++) {
                if ((whiteTurn &&
                        boardConfig[ii][jj].piece%2 == 0 &&
                        boardConfig[ii][jj].piece != 0) ||
                        (!whiteTurn &&
                                boardConfig[ii][jj].piece%2 == 1))
                    break;
                else if ((whiteTurn &&
                        boardConfig[ii][jj].piece%2 == 1) ||
                        (!whiteTurn &&
                                boardConfig[ii][jj].piece%2 == 0 &&
                                boardConfig[ii][jj].piece != 0)) {
                    boardConfig[ii][jj].BG = 1;
                    break;
                }else
                    boardConfig[ii][jj].BG = 1;
            }
            for (int ii=i-1, jj=j+1; ii>-1 && jj<8; ii--, jj++) {
                if ((whiteTurn &&
                        boardConfig[ii][jj].piece%2 == 0 &&
                        boardConfig[ii][jj].piece != 0) ||
                        (!whiteTurn &&
                                boardConfig[ii][jj].piece%2 == 1))
                    break;
                else if ((whiteTurn &&
                        boardConfig[ii][jj].piece%2 == 1) ||
                        (!whiteTurn &&
                                boardConfig[ii][jj].piece%2 == 0 &&
                                boardConfig[ii][jj].piece != 0)) {
                    boardConfig[ii][jj].BG = 1;
                    break;
                }else
                    boardConfig[ii][jj].BG = 1;
            }
        }else if (type/10 == 5) {
            //pawn
            if (whiteTurn)  {
                if (moves<2)
                    boardConfig[i][j-2].BG = 1;
                if (j>0)
                    if (boardConfig[i][j-1].piece == 0)
                        boardConfig[i][j-1].BG = 1;
                if (i>0 && j>0)
                    if (boardConfig[i-1][j-1].piece%2 == 1)
                        boardConfig[i-1][j-1].BG = 1;
                if (i<7 && j>0)
                    if (boardConfig[i+1][j-1].piece%2 == 1)
                        boardConfig[i+1][j-1].BG = 1;
            }else   {
                if (moves<2)
                    boardConfig[i][j+2].BG = 1;
                if (j<7)
                    if (boardConfig[i][j+1].piece == 0)
                        boardConfig[i][j+1].BG = 1;
                if (i>0 && j<7)
                    if (boardConfig[i-1][j+1].piece%2 == 0 && boardConfig[i-1][j+1].piece != 0)
                        boardConfig[i-1][j+1].BG = 1;
                if (i<7 && j<7)
                    if (boardConfig[i+1][j+1].piece%2 == 0 && boardConfig[i+1][j+1].piece != 0)
                        boardConfig[i+1][j+1].BG = 1;
            }
        }
    }

    private void noPossibleMovements()  {
        selected.x = -1;
        selected.y = -1;

        for (int j=0; j<8; j++)  {
            for (int i=0; i<8; i++) {
                boardConfig[i][j].BG = 0;
            }
        }
    }

    private void movePiece(int i, int j)    {
        boardConfig[i]         [j]         .piece = boardConfig[selected.x][selected.y].piece;
        boardConfig[selected.x][selected.y].piece = 0;
        noPossibleMovements();
        whiteTurn = !whiteTurn;
        moves++;
    }
}
