package interfaces;

public class Patterns {
	
	public interface IObservable<T>{
		void notifyObserver();
		void setObserver (T observer);
	}
	
	public interface IObserver<T>{
		void onNotified(T observable);
	}
	
	public interface ICallback<T>{
		void onCalledBack(T arg);
	}
	
}
