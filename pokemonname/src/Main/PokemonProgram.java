package Main;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import MP3Player프로그램.Music;
import Model.MemberDAO;
import Model.MemberDTO;
import Model.PokemonBook;
import Model.MusicCon;
import Model.PokemonDAO;
import Model.PokemonDTO;
import Model.PokemonMusic;
import Model.ScoreDAO;
import Model.ScoreDTO;
import Model.Prologue;
import Model.PokemonBook;
import Model.PokemonBookAns;
import Model.PokemonBookHint;
//import Model.TimeLImitQuiz;
import javazoom.jl.player.MP3Player;
import Model.PokemonBookHint;

public class PokemonProgram {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		String defaultPath = "C:\\Users\\SMHRD\\Desktop\\교육자료\\자바\\music\\";
		PokemonMusic m1 = new PokemonMusic(defaultPath + "Pokemon Fire Red and Leaf Green - Wild Pokemon Battle.mp3",
				"Wild Pokemon Battle");
		ArrayList<PokemonMusic> musicList = new ArrayList<>();
		musicList.add(m1);
		MP3Player bgm = new MP3Player();

		MemberDAO mdao = new MemberDAO(); // mdao 초기화 - 명택
		ScoreDAO sdao = new ScoreDAO(); // sdao 초기화 - 현우
		PokemonBook book = new PokemonBook();

		Prologue prol = new Prologue();
		prol.start();

		int index = 0;
		MusicCon con = new MusicCon();
		con.musicPlay(index, musicList);

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

				// =======플레이
				if (result != null) {
					System.out.println("===메뉴 선택===");
					System.out.println("[1]플레이 [2]랭킹보기 [3]나의 기록 확인 [4]로그아웃 >>");
					int menu = sc.nextInt();
					if (menu == 1) {

						int sum = 0;
						int hint = 5;
						int pass = 1;
						
						PokemonBook pb = new PokemonBook();
						String[] pb1 = PokemonBook.book();
						Random rd = new Random();
						int i=0;
						
						System.out.println("난이도를 선택하라고! 난이도에 따라 점수가 다를 거야!!");
						System.out.println("[1]하 [2]중 [3]상");
						
						int level = sc.nextInt();
						
						int quenum;
						
						if(level == 1) { 
							quenum = rd.nextInt(22);
						}
						else if(level == 2)
						{
							quenum = rd.nextInt(21)+23;
						}
						else
						{
							quenum = rd.nextInt(24)+45;
						}
						
						String question = pb1[quenum];
						System.out.println(question);
						
						
						System.out.println("내 이름이 뭐게!!!");
	
						System.out.println("[1]정답입력 [2]힌트 [3]패스");
						int achoice = sc.nextInt();
						PokemonBookAns pba = new PokemonBookAns();
						String [] pbans = PokemonBookAns.ans();
						String answer = pbans[quenum];
						
						
						if(achoice == 1) {
					
						String userans = sc.next();
							if(userans.equals(answer))
							{
								System.out.println("정답이야!");
								sum=sum+10;
							}
							else
							{
								System.out.println("그것도 기억 못하다니!!");
							}
							i++;
						}
						
						else if(achoice == 2&&hint>0)
						{
							 PokemonBookHint phb = new PokemonBookHint();
							 String [] pbhint = PokemonBookHint.hint();
							 System.out.println("내 이름은 "+pbhint[quenum]+"이니까 잘 맞춰보라고!");
							 hint --;
							 
								String userans = sc.next();
								if(userans.equals(answer))
								{
									System.out.println("정답이야!");
									sum=sum+5;
								}
								else
								{
									System.out.println("그것도 기억 못하다니!!");
								}
								i++;	 
							
								
						}
						else if(achoice == 3 && pass > 0)
						{
							pass --;
						}
						
						if(pass ==0)
						{
							System.out.println("패스 기회를 소진했어! 뭐든 기억해내라고!!");
							String userans = sc.next();
							if(userans.equals(answer))
							{
								System.out.println("정답이야!");
								sum=sum+5;
							}
							else
							{
								System.out.println("그것도 기억 못하다니!!");
							}
							i++;
						}
						

						 if(achoice == 2&&hint ==0)
						 {
							 System.out.println("힌트 기회를 소진했어!! 뭐든 생각해내라고!");
								String userans = sc.next();
								if(userans.equals(answer))
								{
									System.out.println("정답이야!");
									sum=sum+5;
								}
								else
								{
									System.out.println("그것도 기억 못하다니!!");
								}
								i++;

						 }

						ScoreDTO sdto = new ScoreDTO();
						sdto.setId(result.getId());
						sdto.setScore(sum);
						
						int cnt = sdao.PlayScore(sdto);{
							if(cnt>0) {
								System.out.println("점수 저장 성공");
							}
						}
						
						if(i==10)
							{
								System.out.println(sum);
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

			} else break;

		}

	}

}
