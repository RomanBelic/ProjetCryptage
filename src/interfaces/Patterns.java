package interfaces;

public class Patterns {
	
	public interface ICallback<T>{
		void onCalledBack(T arg);
	}
	
	public interface IDelegate<T,S>{
		T action(S arg);
	}
}
