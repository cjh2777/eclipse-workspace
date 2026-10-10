using System;
using UnityEngine;

// 현재 붓 색 + HSV 슬라이더 (예상 구현)
public class ColorPalette : MonoBehaviour
{
    public event Action<Color> OnColorChanged;
    public Color Current { get; private set; } = Color.white;

    public void SetCurrent(Color color)
    {
        Current = color;
        OnColorChanged?.Invoke(color);
    }

    public void SetFromHsv(float h, float s, float v)
    {
        SetCurrent(Color.HSVToRGB(h, s, v));
    }
}
