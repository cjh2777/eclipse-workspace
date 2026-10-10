using System.Collections;
using UnityEngine;

// 스포이트: 화면 중앙(조준점)에 보이는 색을 그대로 뽑는다 (예상 구현)
// 텍스처 색이 아니라 "조명까지 적용된 화면 색"을 뽑아야 술래 눈에도 똑같이 보이므로
// 렌더링이 끝난 뒤 화면 픽셀을 읽는 방식이 자연스럽다.
public class Eyedropper : MonoBehaviour
{
    [SerializeField] private ColorPalette palette;
    private Texture2D onePixel;

    private void Awake()
    {
        onePixel = new Texture2D(1, 1, TextureFormat.RGB24, false);
    }

    public void Pick()
    {
        StartCoroutine(PickAtEndOfFrame());
    }

    private IEnumerator PickAtEndOfFrame()
    {
        yield return new WaitForEndOfFrame();
        int x = Screen.width / 2;
        int y = Screen.height / 2;
        onePixel.ReadPixels(new Rect(x, y, 1, 1), 0, 0);
        onePixel.Apply();
        palette.SetCurrent(onePixel.GetPixel(0, 0));
    }
}
