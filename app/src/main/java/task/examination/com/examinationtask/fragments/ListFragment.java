package task.examination.com.examinationtask.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import task.examination.com.examinationtask.R;

public class ListFragment extends Fragment
{
    private static final String TAG = ListFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override public View
    onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        Button button = view.findViewById(R.id.task1_button);
        button.setOnClickListener(v -> SendOrder());

        button = view.findViewById(R.id.task2_button);
        button.setOnClickListener(v -> SendProduct());

        return view;
    }

    // интерфейс для доступа к активности
    public interface OnFragmentInteractionListener
    {
        void onFragmentInteraction(String link);
    }

    // доступ к активности - получить ссылку на активность, но
    // требуется реализация активностью интерфейса OnFragmentInteractionListener
    @Override public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            mListener = (OnFragmentInteractionListener) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(
                context.toString() + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }

    // уведомелние активности о создании данных
    public void updateDetail()
    {
        String action = "пусто";

        // Посылаем данные MainActivity - активность в обработчике
        // будет принимать решение: обновлять существующий фрагмент
        // или создавать другую активность
        mListener.onFragmentInteraction(action);
    }

    public void SendOrder()
    {
        String action = "Заказы";

        mListener.onFragmentInteraction(action);
    }

    public void SendProduct()
    {
        String action = "Товары";

        mListener.onFragmentInteraction(action);
    }
}
