package fn;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Flib {
  public static <S,T> List<T> map(List<S> list, L<S,T> lambda) {
    List<T> result = new ArrayList<T>();
    for (S s : list) {
      result.add(lambda.call(s));
    }
    return result;
  }

  public static void main(String[] args) {
	  System.out.println(Flib.map(Arrays.asList(3,5,1,6),new L<Integer,String>(){
		  public String call(Integer arg) {
			  return ""+arg+"--";
		  }
	  }));
  }
}
