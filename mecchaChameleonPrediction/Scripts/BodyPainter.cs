using System.Collections.Generic;
using UnityEngine;

// 숨는 사람의 흰 몸에 붓으로 색을 칠하는 컴포넌트 (예상 구현)
// 캐릭터 메시에 MeshCollider가 있어야 RaycastHit.textureCoord로 UV를 얻을 수 있다.
[RequireComponent(typeof(Renderer), typeof(MeshCollider))]
public class BodyPainter : MonoBehaviour
{
    public const int TextureSize = 512;

    [SerializeField] private float brushRadius = 0.03f; // UV 공간 기준 반지름
    [SerializeField] private float brushHardness = 0.7f;

    private Texture2D paintTexture;
    private Color32[] pixels;
    private bool dirty;

    // 네트워크로는 텍스처 대신 획(stroke)만 보낸다고 예상 (512x512 텍스처를 매번 보내긴 너무 큼)
    public readonly List<PaintStroke> history = new List<PaintStroke>();

    private void Awake()
    {
        paintTexture = new Texture2D(TextureSize, TextureSize, TextureFormat.RGBA32, false);
        paintTexture.wrapMode = TextureWrapMode.Clamp;
        pixels = new Color32[TextureSize * TextureSize];
        Clear(Color.white);
        GetComponent<Renderer>().material.mainTexture = paintTexture;
    }

    public void Clear(Color color)
    {
        Color32 c = color;
        for (int i = 0; i < pixels.Length; i++) pixels[i] = c;
        history.Clear();
        dirty = true;
    }

    // 카메라에서 레이를 쏴서 몸에 맞은 위치에 칠한다
    public bool TryPaint(Ray ray, Color color)
    {
        if (!Physics.Raycast(ray, out RaycastHit hit, 10f) || hit.collider.gameObject != gameObject)
            return false;
        PaintAt(hit.textureCoord, color, brushRadius);
        history.Add(new PaintStroke(hit.textureCoord, color, brushRadius));
        return true;
    }

    public void PaintAt(Vector2 uv, Color color, float radius)
    {
        int cx = (int)(uv.x * TextureSize);
        int cy = (int)(uv.y * TextureSize);
        int r = Mathf.CeilToInt(radius * TextureSize);

        for (int y = cy - r; y <= cy + r; y++)
        {
            if (y < 0 || y >= TextureSize) continue;
            for (int x = cx - r; x <= cx + r; x++)
            {
                if (x < 0 || x >= TextureSize) continue;
                float d = Mathf.Sqrt((x - cx) * (x - cx) + (y - cy) * (y - cy)) / r;
                if (d > 1f) continue;
                // 가장자리는 부드럽게 섞이도록
                float alpha = d < brushHardness ? 1f : 1f - (d - brushHardness) / (1f - brushHardness);
                int i = y * TextureSize + x;
                pixels[i] = Color32.Lerp(pixels[i], color, alpha);
            }
        }
        dirty = true;
    }

    private void LateUpdate()
    {
        // 한 프레임에 여러 번 칠해도 GPU 업로드는 한 번만
        if (!dirty) return;
        paintTexture.SetPixels32(pixels);
        paintTexture.Apply(false);
        dirty = false;
    }
}

[System.Serializable]
public struct PaintStroke
{
    public Vector2 uv;
    public Color32 color;
    public float radius;

    public PaintStroke(Vector2 uv, Color32 color, float radius)
    {
        this.uv = uv;
        this.color = color;
        this.radius = radius;
    }
}
