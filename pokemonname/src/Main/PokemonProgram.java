package Main;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//import MP3Player프로그램.Music;
import Model.MemberDAO;
import Model.MemberDTO;
//import Model.MusicCon;
import Model.PokemonDAO;
import Model.PokemonDTO;
//import Model.PokemonMusic;
import Model.ScoreDAO;
import Model.ScoreDTO;
import Model.Prologue;
import Model.PokemonBook;
import Model.PokemonBookAns;
//import Model.TimeLImitQuiz;
//import javazoom.jl.player.MP3Player;

public class PokemonProgram {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
//		String defaultPath = "C:\\Users\\SMHRD\\Desktop\\music\\";
//		PokemonMusic m1 = new PokemonMusic(defaultPath +"Pokemon Fire Red and Leaf Green - Wild Pokemon Battle.mp3","Wild Pokemon Battle");
//		ArrayList<PokemonMusic> musicList = new ArrayList<>();
//		musicList.add(m1);
//		MP3Player bgm = new MP3Player();

		MemberDAO mdao = new MemberDAO(); // mdao 초기화 - 명택
		ScoreDAO sdao = new ScoreDAO(); // sdao 초기화 - 현우
		
		Prologue prol = new Prologue();
		prol.start();
		
		int index = 0;
//		MusicCon con = new MusicCon();
//		con.musicPlay(index, musicList);
		
		
		String skip = sc.next();
		
		while (true) {
			
			prol.stop();
			System.out.println();
			System.out.println("포켓몬 너의 이름은");
			

			
			System.out.print("[1]회원가입 [2]로그인 [3]종료 >>");
			int choice = sc.nextInt();

			if (choice == 1) {
				
				System.out.print("가입할 아이디 입력 : ");
				String joinId = sc.next();

				int result = mdao.idCheck(joinId);
				if (result == 0) {
					System.out.println("사용가능한 id입니다.");
				} else {
					System.out.println("사용중인 id 입니다. 다시 입력해주세요");
					continue; // 중복인 id를 받으면 while문 처음이 아닌 "가입할 아이디 입력으로 갔으면 좋겠는데 방법을 모르겠음
				}
				

				System.out.print("가입할 비밀번호 입력 : ");
				String joinPw = sc.next();

				MemberDTO dto = new MemberDTO();
				dto.setId(joinId);
				dto.setPw(joinPw);
				
				int cnt = mdao.join(dto);

			} else if (choice == 2) {
				System.out.print("아이디 입력 : ");
				String id = sc.next();
				System.out.print("비밀번호 입력 : ");
				String pw = sc.next();

				MemberDTO dto = new MemberDTO();
				dto.setId(id);
				dto.setPw(pw);

				MemberDTO result = mdao.login(dto);

				

				//=======플레이
				if(result != null) {
					System.out.println("===메뉴 선택===");
					System.out.println("[1]플레이 [2]랭킹보기 [3]나의 기록 확인 [4]로그아웃 >>");
					int menu = sc.nextInt();
					if(menu==1) {
						int sum = 0;
						
						PokemonBook pb = new PokemonBook();
						String [] pb1 = PokemonBook.book();
						Random rd = new Random();
						int i=0;
						
						while(i<10) {
						int quenum = rd.nextInt(pb1.length);
						String question = pb1[quenum];
						System.out.println(question);
						System.out.println("내 이름이 뭐게!!!");
						
						PokemonBookAns pba = new PokemonBookAns();
						String [] pbans = PokemonBookAns.ans();
						String answer = pbans[quenum];
						String userans = sc.next();
						if(userans.equals(answer))
						{
							System.out.println("정답이야!");
							sum++;
						}
						else
						{
							System.out.println("그것도 기억 못하다니!!");
						}
						i++;
						}
						
						if(i==10)
						{
							System.out.println(sum);
						}

						ScoreDTO sdto = new ScoreDTO();
						sdto.setId(result.getId());
						sdto.setScore(sum);
						
						int cnt = sdao.PlayScore(sdto);{
							if(cnt>0) {
								System.out.println("점수 저장 성공");
							}
						}
						
					}
					else if(menu==2) {
						
						ArrayList<ScoreDTO> list=sdao.rank();
						
						System.out.println("순위\t아이디\t점수");
						for(int i = 0;i<list.size();i++) {
							
							//ScoreDTO dto1 = list.get(i);
					    	System.out.println((i+1)+"\t" +list.get(i).getId() +"\t" + list.get(i).getScore());
					    }
					
					}else if(menu==3) {
						ArrayList<ScoreDTO> list = sdao.history(result.getId());
						
						System.out.println("점수\t날짜");
						for(int i=0;i<list.size();i++) {
							System.out.println(list.get(i).getScore()+"\t"+list.get(i).getIndate());
						}
						
					}else break;
//						
//					

				}
				
				
				
			}else break;

		}

	}

}
