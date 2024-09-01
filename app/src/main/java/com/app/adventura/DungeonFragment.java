package com.app.adventura;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.app.adventura.databinding.FragmentDungeonBinding;

import java.util.Random;

public class DungeonFragment extends Fragment {

    private FragmentDungeonBinding binding;
    private Random random;
    private int monsterHealth;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentDungeonBinding.inflate(inflater, container, false);
        random = new Random();

        MainActivity.health = 100; // Health resets every round
        binding.yourHealth.setText(getString(R.string.level) + " " + MainActivity.level + ", " + getString(R.string.your_health) + ": " + MainActivity.health);
        binding.rollDice.setVisibility(View.VISIBLE);

        switch (MainActivity.level){
            case 1:
                binding.monsterImage.setImageResource(R.drawable.lvl1);
                break;
            case 2:
                binding.monsterImage.setImageResource(R.drawable.lvl2);
                break;
            case 3:
                binding.monsterImage.setImageResource(R.drawable.lvl3);
                break;
            case 4:
                binding.monsterImage.setImageResource(R.drawable.lvl4);
                break;
            case 5:
                binding.monsterImage.setImageResource(R.drawable.lvl5);
                break;
            case 6:
                binding.monsterImage.setImageResource(R.drawable.lvl6);
                break;
            case 7:
                binding.monsterImage.setImageResource(R.drawable.lvl7);
                break;
            case 8:
                binding.monsterImage.setImageResource(R.drawable.lvl8);
                break;
            default:
                binding.monsterImage.setImageResource(R.drawable.lvl9);
                break;
        }

        return binding.getRoot();
    }

    private void MonsterDamage () {
        int damage = random.nextInt(15); // minimum 0, maximum 15, monster might miss and do 0 daamge
        binding.rolled.setText(binding.rolled.getText() + ", " + getString(R.string.monster_dealt) + " " + damage + " " + getString(R.string.damage));
        MainActivity.health -= damage;
        if(MainActivity.health < 0) MainActivity.health = 0;
        binding.yourHealth.setText(getString(R.string.level) + " " + MainActivity.level + ", " + getString(R.string.your_health) + ": " + MainActivity.health);
        if(MainActivity.health == 0){
            binding.result.setText(getText(R.string.you_died));
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    MainActivity.level = 1;
                    NavHostFragment.findNavController(DungeonFragment.this)
                            .navigate(R.id.action_DungeonFragment_to_FirstFragment);
                }
            }, 1000);
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        monsterHealth = random.nextInt(30 + MainActivity.level*10) + 30; // minimum health 30, maximum 30 + level*10
        binding.monsterHealth.setText(getString(R.string.monster_health) + ": " + monsterHealth);

        binding.rollDice.setOnClickListener(v -> {
                int roll = random.nextInt(10) + 1; // from 1 to 10
                binding.rolled.setText(getString(R.string.you_dealt) + " " + roll + " " + getString(R.string.damage));
                monsterHealth -= roll;
                if(monsterHealth < 0) monsterHealth = 0;
                binding.monsterHealth.setText(getString(R.string.monster_health) + ": " + monsterHealth);
                if(monsterHealth == 0){
                    binding.rollDice.setVisibility(View.INVISIBLE);
                    binding.result.setText(getText(R.string.monster_died));
                    MainActivity.level += 1;

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            NavHostFragment.findNavController(DungeonFragment.this)
                                    .navigate(R.id.action_DungeonFragment_to_DungeonFragment);
                        }
                    }, 1000);
                }
                else {
                    MonsterDamage();
                }
            }
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}