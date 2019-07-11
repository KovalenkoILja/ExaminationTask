package task.examination.com.examinationtask.utilities;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

public class MinMaxInputFilter implements InputFilter
{
    private double mMinValue;
    private double mMaxValue;

    private static final double MIN_VALUE_DEFAULT = Double.MIN_VALUE;
    private static final double MAX_VALUE_DEFAULT = Double.MAX_VALUE;

    public MinMaxInputFilter(Double min, Double max)
    {
        this.mMinValue = (min != null ? min : MIN_VALUE_DEFAULT);
        this.mMaxValue = (max != null ? max : MAX_VALUE_DEFAULT);
    }

    public MinMaxInputFilter(Integer min, Integer max)
    {
        this.mMinValue = (min != null ? min : MIN_VALUE_DEFAULT);
        this.mMaxValue = (max != null ? max : MAX_VALUE_DEFAULT);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend)
    {
        try
        {
            String replacement = source.subSequence(start, end).toString();
            String newVal = dest.subSequence(0, dstart).toString() + replacement
                    + dest.subSequence(dend, dest.length()).toString();

            if (newVal.matches("0\\d+.*"))
                if (TextUtils.isEmpty(source))
                    return dest.subSequence(dstart, dend);
                else
                    return "";

            double input = Double.parseDouble(newVal);
            if (!isInRange(mMinValue, mMaxValue, input))
                if (TextUtils.isEmpty(source))
                    return dest.subSequence(dstart, dend);
                else
                    return "";

            return null;
        }
        catch (NumberFormatException ignored) { }
        return "";
    }

    private boolean isInRange(double a, double b, double c)
    {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}
