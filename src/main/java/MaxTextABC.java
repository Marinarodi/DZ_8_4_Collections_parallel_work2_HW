public class MaxTextABC<T, I> {
    protected T text;
    protected I count;

    public void setText(T text) {
        this.text = text;
    }

    public void setCount(I count) {
        this.count = count;
    }

    public T getText() {
        return text;
    }

    public I getCount() {
        return count;
    }
}
