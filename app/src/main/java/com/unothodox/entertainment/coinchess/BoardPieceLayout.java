package com.unothodox.entertainment.coinchess;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class BoardPieceLayout extends ArrayAdapter<GameActivity.boardPiece> {
    BoardPieceLayout(Context context, ArrayList<GameActivity.boardPiece> objects) {
        super(context, R.layout.layout_board_piece, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inf = LayoutInflater.from(getContext());
        @SuppressLint("ViewHolder")
        View v = inf.inflate(R.layout.layout_board_piece, parent, false);
        Resources res = getContext().getResources();

        GameActivity.boardPiece e = getItem(position);

        View v_BG = v.findViewById(R.id.v_BG);
        View v_piece = v.findViewById(R.id.v_piece);



        assert e != null;
        switch (e.BG)   {
            case    0:
                v_BG.setBackground(res.getDrawable(R.drawable.bg_0));
                break;
            case    1:
                v_BG.setBackground(res.getDrawable(R.drawable.bg_1));
                break;
        }

        switch (e.piece)    {
            case    1:
                v_piece.setBackground(res.getDrawable(R.drawable.piece_01));
                break;
            case    2:
                v_piece.setBackground(res.getDrawable(R.drawable.piece_02));
                break;
            case    11:
                v_piece.setBackground(res.getDrawable(R.drawable.piece_11));
                break;
            case    12:
                v_piece.setBackground(res.getDrawable(R.drawable.piece_12));
                break;
            case    21:
                v_piece.setBackground(res.getDrawable(R.drawable.piece_21));
                break;
            case    22:
                v_piece.setBackground(res.getDrawable(R.drawable.piece_22));
                break;
            case    31:
                v_piece.setBackground(res.getDrawable(R.drawable.piece_31));
                break;
            case    32:
                v_piece.setBackground(res.getDrawable(R.drawable.piece_32));
                break;
            case    41:
                v_piece.setBackground(res.getDrawable(R.drawable.piece_41));
                break;
            case    42:
                v_piece.setBackground(res.getDrawable(R.drawable.piece_42));
                break;
            case    51:
                v_piece.setBackground(res.getDrawable(R.drawable.piece_51));
                break;
            case    52:
                v_piece.setBackground(res.getDrawable(R.drawable.piece_52));
                break;
            default:
                v_piece.setBackground(null);
        }

        return v;
    }
}
