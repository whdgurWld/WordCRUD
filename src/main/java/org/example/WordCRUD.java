package org.example;

import java.io.*;


import java.util.ArrayList;
import java.util.Scanner;

public class WordCRUD implements ICRUD{
    ArrayList<Word> list;
    Scanner s;
    final String fileName = "Dictionary.txt";

    WordCRUD(Scanner s){
        list = new ArrayList<>();
        this.s = s;
    }

    @Override
    public Object add() {
        System.out.print("=> 난이도(1,2,3) & 새 단어 입력 : ");
        int level = s.nextInt();
        String word = s.nextLine();

        System.out.print("뜻 입력 : ");
        String meaning = s.nextLine();

        return new Word(0, level, word, meaning);
    }

    public void addWord(){
        Word word = (Word) add();
        list.add(word);
        System.out.println("새 단어가 단어장에 추가되었습니다!!!");
    }

    public void listAll(){
        System.out.println("-----------------------");
        for(int i = 0; i < list.size(); i++){
            System.out.print((i+1) + " ");
            System.out.println(list.get(i).toString());
        }
        System.out.println("-----------------------");
    }
    public ArrayList<Integer> listAll(String keyword){
        ArrayList<Integer> idList = new ArrayList<>();

        int j = 0;
        System.out.println("-----------------------");
        for(int i = 0; i < list.size(); i++){
            String word = list.get(i).getWord();
            if(!word.contains(keyword)) continue;

            System.out.print((j + 1) + " ");
            System.out.println(list.get(i).toString());
            idList.add(i);
            j++;

        }
        System.out.println("-----------------------");

        return idList;
    }
    public void listAll(int level){

        int j = 0;
        System.out.println("-----------------------");
        for (Word word : list) {
            int ilevel = word.getLevel();
            if (ilevel != level) continue;

            System.out.print((j + 1) + " ");
            System.out.println(word.toString());
            j++;

        }
        System.out.println("-----------------------");

    }

    public void updateItem() {
        System.out.print("=> 수정할 단어 검색 : ");
        String keyword = s.next();
        ArrayList<Integer> idList = this.listAll(keyword);

        System.out.print("=> 수정할 번호 선택 : ");
        int id = s.nextInt();
        s.nextLine();

        System.out.print("=> 뜻 입력 : ");
        String meaning = s.nextLine();
        Word word = list.get(idList.get(id-1));
        word.setMeaning(meaning);
        System.out.println("단어 수정이 성공적으로 되었습니다!!");

    }

    public void deleteItem() {
        System.out.print("=> 삭제할 단어 검색 : ");
        String keyword = s.next();
        ArrayList<Integer> idList = this.listAll(keyword);

        System.out.print("=> 삭제할 번호 선택 : ");
        int id = s.nextInt();
        s.nextLine();

        System.out.print("=> 정말로 삭제하실래요?(Y/n) : ");
        String ans = s.nextLine();

        if(ans.equalsIgnoreCase("y")) {
            list.remove((int) idList.get(id-1));
            System.out.println("선택한 단어 삭제 완료 !!!");
        }
        else {
            System.out.println("취소되었습니다. ");
        }
    }

    public void loadFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            String line;

            int count = 0;
            while(true) {
                line = br.readLine();
                if(line == null) break;

                String[] data = line.split("\\|");
                int level = Integer.parseInt(data[0]);
                String word = data[1];
                String meaning = data[2];
                list.add(new Word(0, level, word, meaning));
                count++;
            }
            br.close();
            System.out.println("==> " + count + "개 로딩 완료!!!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveFile() {
        PrintWriter pr;
        try {
            pr = new PrintWriter(new FileWriter("Dictionary.txt"));

            for (Word one : list) {
                pr.write(one.toFileString() + "\n");
            }
            pr.close();
            System.out.println("모든 단어 파일 저장 완료 !!!");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void searchLevel() {
        System.out.print("=> 레벨(1:초급, 2:중급, 3:고급) 선택 : ");
        int level = s.nextInt();
        listAll(level);
    }
    public void searchWord() {
        System.out.print("=> 원하는 단어는? ");
        String keyword = s.next();
        listAll(keyword);
    }


    @Override
    public int update(Object obj) {
        return 0;
    }

    @Override
    public int delete(Object obj) {
        return 0;
    }

    @Override
    public void selectOne(int id) {

    }
}
